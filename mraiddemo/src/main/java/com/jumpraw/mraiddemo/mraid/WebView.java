package com.jumpraw.mraiddemo.mraid;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.jumpraw.mraiddemo.Utils.Assets;
import com.jumpraw.mraiddemo.Utils.Defaults;
import com.jumpraw.mraiddemo.Utils.PageAdVO;

import java.io.ByteArrayInputStream;
import java.util.Formatter;
import java.util.Locale;

import androidx.annotation.RequiresApi;

import static android.content.Context.MODE_PRIVATE;

public class WebView extends android.webkit.WebView {

    private static final String MRAID_JAVASCRIPT_INTERFACE_NAME = "MASTMRAIDWebView";
    private Handler handler = null;
    private boolean loaded = false;
    private Context context;

    // For API10 and lower a string, for API11 and higher an InputStream
    private String mraidBridgeJavascript = null;
    private Bridge bridge;

    private boolean webviewFailed = false;
    private final static long delayTime = 60000L;
    private final static int errorCode = -1001;
    private final static String errMsg = "TimeOut";
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!loaded && handler != null) {
                webviewFailed = true;
                handler.webViewReceivedError(null, errorCode, errMsg, null);
            }
        }
    };

    /**
     * 构造方法中，对本webview做参数设置
     */
    @SuppressLint("SetJavaScriptEnabled")
    public WebView(Context context) {
        super(context);
        this.context = context;

        setBackgroundColor(Color.TRANSPARENT);
        setWebViewClient(new ViewClient());
        setWebChromeClient(new ChromeClient());
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 16);//设置缓冲大小，设的是16M
        String appCacheDir = getContext().getApplicationContext().getDir("cache", MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCacheDir);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //允许加载http与https混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //进入webview时需要自动播放h5里的video视频
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        //开启调试web和js模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }

        setOnTouchListener(new TouchListener());
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    /**
     * 设置WebviewClient回调接口对象， WebView.Handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (handler != null) {
            //webview展示到屏幕上
            handler.webViewExposureChange(this, bridge, isShown());
        }
    }


    /**
     * webview加载fragment数据
     *
     * @param adVO
     * @param bridge
     * @throws Exception
     */
    @SuppressLint({"AddJavascriptInterface", "JavascriptInterface"})
    public void loadFragment(PageAdVO adVO, Bridge bridge) {
        //js-native桥
        this.bridge = bridge;
        //为webview设置js-native桥
        addJavascriptInterface(bridge, MRAID_JAVASCRIPT_INTERFACE_NAME);

        //广告数据
        String fragment = adVO.pagedAd.html_tag.trim();
        String manifest = adVO.pagedAd.manifest;
        if (!TextUtils.isEmpty(manifest)) {
            manifest = "manifest=\"" + manifest + "\"";
        }
        String content;

        //api 11之上和之前版本不同
        Formatter formatter = new Formatter(Locale.US);
        formatter.format(Defaults.RICHMEDIA_FORMAT, manifest, mraidBridgeJavascript, fragment);
        content = formatter.toString();
        formatter.close();

        //格式化html
        content = MRAIDHtmlProcessor.processRawHtml(content);
        if (content != null) {
            content = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
        }
        loadData(content, "text/html; charset=UTF-8", "base64");

//        loadUrl("file:///android_asset/demo.html");
    }


    /**
     * todo 调用执行js方法
     */
    public void injectJavascript(String script) {
        final String url = "javascript:" + script;
        Defaults.handler.post(new Runnable() {
            public void run() {
                loadUrl(url);
            }
        });
    }


    //是否load
    public boolean isLoaded() {
        return loaded;
    }


    private class TouchListener implements OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                    }
                    break;

                default:
                    break;
            }

            return false;
        }
    }


    private class ViewClient extends WebViewClient {

        public ViewClient() {
            initJavascriptBridge();
        }

        private void initJavascriptBridge() {
            if (mraidBridgeJavascript == null) {
                mraidBridgeJavascript = Assets.getMraidJs(context);
            }
        }

        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loaded = false;
            Defaults.handler.postDelayed(runnable, delayTime);  //60秒没有走到onPageFinished，认为加载失败，手动回调webViewReceivedError

            if (handler != null) {
                handler.webViewPageStarted((WebView) view);
            }
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
            loaded = true;

            if (!webviewFailed && handler != null) {    //走到onPageFinished的时候，如果还没有超过60秒，移除handle消息，回调onPageFinished
                Defaults.handler.removeCallbacks(runnable);
                handler.webViewPageFinished((WebView) view);
            }

            view.setFocusableInTouchMode(true);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(android.webkit.WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            webviewFailed = true;
            if (handler != null) {
                handler.webViewReceivedError((WebView) view, errorCode, description, failingUrl);
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(android.webkit.WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (request.isForMainFrame()) {
                onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            boolean override = false;
            if (handler != null) {
                override = handler.webViewShouldOverrideUrlLoading((WebView) view, url);
            }
            return override;
        }


        @Override
        public WebResourceResponse shouldInterceptRequest(android.webkit.WebView webView, String url) {
            if ((!TextUtils.isEmpty(url)) && url.contains("mraid.js")) {
                return new WebResourceResponse("text/javascript", "UTF-8", new ByteArrayInputStream(mraidBridgeJavascript.getBytes()));
            }
            return super.shouldInterceptRequest(webView, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, WebResourceRequest request) {
            if (request != null && request.getUrl() != null && !TextUtils.isEmpty(request.getUrl().getPath()) && request.getUrl().getPath().contains("mraid.js")) {
                return new WebResourceResponse("text/javascript", "UTF-8", new ByteArrayInputStream(mraidBridgeJavascript.getBytes()));
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
            handler.cancel();
        }

    }


    private class ChromeClient extends WebChromeClient {

    }


    public interface Handler {
        void webViewPageStarted(WebView webView);

        void webViewPageFinished(WebView webView);

        void webViewReceivedError(WebView webView, int errorCode, String description, String failingUrl);

        boolean webViewShouldOverrideUrlLoading(WebView view, String url);

        void webViewExposureChange(WebView view, Bridge bridge, boolean visible);
    }
}
