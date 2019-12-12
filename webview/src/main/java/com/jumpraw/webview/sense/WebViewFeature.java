package com.jumpraw.webview.sense;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jumpraw.webview.LogUtil;
import com.jumpraw.webview.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 检测webview真正加载完成
 * <p>
 * note1: webview在回调onPageFinished时，可能还有页面资源未加载完成，比如adsense广告
 * note2: webview在loadUrl之后，页面多次重定向时可能也会走到onPageFinished
 */
public class WebViewFeature {

    private Context mContext;
    private SenseVo mSenseVo;

    private WebView mWebView;
    private int currentJSIndex = 0;

    private int mPageProgress;
    private int mLastPageProgress;
    private String webViewErrorInfo;
    private boolean mIsPageFinished;

    private Timer timer;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final long DELAY_CHECK_FINISH = 10 * 1000;
    private static final long DELAY_CHECK_NOACTION = 35 * 1000;

    public WebViewFeature(Context context, SenseVo senseVo) {
        mContext = context;
        mSenseVo = senseVo;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void startLoadUrl(WebView webView) {
        if (mContext == null || mSenseVo == null || TextUtils.isEmpty(mSenseVo.url)) {
            return;
        }

        mWebView = webView;

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        JRJSBridge.addToWebView(mContext, mWebView);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtil.d("onPageStarted----url=" + url);
                //关闭检查任务
                stopTask();

                if (url.startsWith("market://")) {
                    String newUrl = url.replace("market://", "https://play.google.com/store/apps/");
                    view.loadUrl(newUrl);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.d("Override Url Loading----url:" + url);
                return Utils.isTragetUrl(url) || url.endsWith(".apk") || super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtil.d("onPageFinished----url=" + url);
                mIsPageFinished = true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                LogUtil.d("shouldInterceptRequest----progress >>" + mPageProgress);
                mPageProgress++;
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mIsPageFinished = true;
                webViewErrorInfo = "web load error: " + description;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {
                    onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.cancel();
            }
        });

        checkPageFinished(true);
        mWebView.loadUrl(mSenseVo.url);
    }


    private Runnable CheckRunnable = new Runnable() {
        @Override
        public void run() {
            handlerCheckPageFinished();
        }
    };

    private void checkPageFinished(boolean isNewPage) {
        if (isNewPage) {
            mIsPageFinished = false;
            webViewErrorInfo = "";
            mPageProgress = 0;
            mLastPageProgress = 0;
            startTask();
        }
        mHandler.postDelayed(CheckRunnable, DELAY_CHECK_FINISH);
    }


    private void handlerCheckPageFinished() {
        LogUtil.i("handlerCheckPageFinished: ");
        try {
            if (mIsPageFinished) {
                if (!TextUtils.isEmpty(webViewErrorInfo)) {
                    return;
                }

                if (mPageProgress != mLastPageProgress) {
                    //页面还没加载完成
                    mLastPageProgress = mPageProgress;
                    checkPageFinished(false);
                    return;
                }

                //真正加载完成
                String[] jsStrs = mSenseVo.jsStrs;
                if (jsStrs == null || jsStrs.length == 0 || currentJSIndex >= jsStrs.length) {
                    //这里结束
                    LogUtil.i("no js to evaluate");
                    return;
                }

                String js = jsStrs[currentJSIndex];
                LogUtil.i("handlerCheckPageFinished: js >>>" + js);
                currentJSIndex++;

                mWebView.evaluateJavascript("javascript:" + js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                        //js执行结束，跳转第二个页面，重新检测
                        checkPageFinished(true);
                    }
                });

            } else {
                checkPageFinished(false);
            }
        } catch (Throwable e) {
            LogUtil.printStackTrace(e);
        }
    }

    //开始check逻辑之后，如果页面未加载成功，一直空循环检查，通过timer关闭
    private synchronized void startTask() {
        stopTask();

        if (timer == null) {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //要执行的任务
                    LogUtil.i("run: removeCallbacks");
                    mHandler.removeCallbacks(CheckRunnable);
                }
            };
            timer.schedule(timerTask, DELAY_CHECK_NOACTION);
        }
    }

    private synchronized void stopTask() {
        try {
            if (timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
