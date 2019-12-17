package com.jumpraw.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 学习webview
 * webview 中加载http/https url，无法通过javascript实现跨域
 */
public class MainActivity extends Activity {

    private static final String TAG = "webviewDemo";
    private Context context;
    private WebView webView;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static String path = Environment.getExternalStorageDirectory() + File.separator + "google.html";
    public static String html;
    public static String jsStr;
    public static String url = "http://youtube.fbunion.com/?channel=50175";
    public static String aibb = "https://offer.alibaba.com/cps/vgcju3f5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
//        checkPermission();

        webView = findViewById(R.id.webview);

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpWebView();

            }
        });


        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadJS();
            }
        });


        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, WebViewActivity.class));

            }
        });
    }


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setUpWebView() {

        //webSetting设置
        WebSettings webSettings = webView.getSettings();
        //支持页面与Javascipt交互，设置为true
        webSettings.setJavaScriptEnabled(true);
        //在onStop和onResume中分别设置成false和true，可以避免在activity失去焦点之后，JS继续执行动画操作等造成的资源浪费

        //设置自适应屏幕，
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);  //缩放至屏幕大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //当加载html页面时，WebView会在/data/data/包名 目录下生成database与cache两个文件夹
        //请求的URL记录保存在WebViewCache.db，而URL的内容是保存在WebViewCache文件夹下
        //设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        //WebSettings.LOAD_CACHE_ONLY  不使用网络，只读取本地缓存数据
        //WebSettings.LOAD_DEFAULT     (默认)根据cache-control决定是否从网络获取数据
        //WebSettings.LOAD_NO_CACHE    不使用缓存，只从网络获取数据
        //WebSettings.LOAD_CACHE_ELSE_NETWORK  只要本地有，无论是否过期，都使用缓存中的数据

        webSettings.setDomStorageEnabled(true); //开启DOM storage API 功能 （默认false）
        webSettings.setDatabaseEnabled(true);   //开启database storage API 功能 （默认false）
        webSettings.setAppCacheEnabled(true);   //开启Application Caches功能 （默认false）
//        webSettings.setAppCachePath();          //设置Application Caches缓存目录，每个application只调用一次


        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);


        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        //支持http/https混合使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new MWebChromeClient(context));
        webView.setWebViewClient(new MWebViewClient());

        //添加js-native调用对象
        webView.addJavascriptInterface(new JumpRawJSBridge(), "jumpraw");


//        webView.loadUrl(url);
        webView.loadUrl("file:///android_asset/demo.html");

//        String htmlStr = getAssets(context, "demo.html");
//        webView.loadData(htmlStr, "text/html", "utf-8");
//        webView.loadDataWithBaseURL("http://www.jcodecraeer.com", htmlStr, "text/html", "utf-8",null);

//        获取页面内容
//        webView.loadUrl("javascript:window.jumpraw.showSource('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//        webView.loadUrl("javascript:window.android.printStatus(document.readyState)");
    }


    private void loadJS() {
        Log.i("test", "loadJS: >>" + jsStr);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            webView.loadUrl("javascript:" + jsStr);

        } else {
            webView.evaluateJavascript("javascript:" + jsStr, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    //有返回值
                    Log.i("test", "onReceiveValue: >> result" + s);
                }
            });
        }
    }


    public String getAssets(Context context, String fileName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes, 0, 1024)) != -1) {
                baos.write(bytes, 0, length);
                baos.flush();
            }

            return baos.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void open(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ComponentName componentName = intent.resolveActivity(App.context.getPackageManager());
        if (componentName != null) {
            App.context.startActivity(intent);
        }
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, 321);
                }
            }

        }
    }


    //物理回退键，用于回退webview
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    // 关于cookie

    /**
     * 获取cookie
     *
     * @param url
     */
    private void getCookies(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.acceptCookie();
        String cookieStr = cookieManager.getCookie(url);

        Log.i(TAG, "onPageFinished: >>" + cookieStr);
    }


//    Load url之前调用 setCookies 方法：

    /**
     * 设置Cookie
     *
     * @param url
     */
    private void setCookies(String url, String strCookies) {
        if (!TextUtils.isEmpty(strCookies)) {
            String arrayCookies[] = strCookies.split(";");
            if (arrayCookies.length > 0) {
                for (String cookie : arrayCookies) {
                    synCookies(url, cookie);
//                    synCookies(this, url, cookie);
                }
            }
        }
    }


    /**
     * 同步Cookie
     *
     * @param url
     * @param cookie 格式：uid=21233 如需设置多个，需要多次调用
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void synCookies(String url, String cookie) {
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url, cookie);
            cookieManager.flush();
        } catch (Exception e) {
            Log.i(TAG, "synCookies: ");
        }
    }


    /**
     * 设置Cookie
     *
     * @param context
     * @param url
     * @param cookie  格式：uid=21233 如需设置多个，需要多次调用
     */
    public void synCookies(Context context, String url, String cookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookie + ";Domain=hotspot.******;Path=/");//cookies格式自定义
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 清除Cookie
     *
     * @param context
     */
    public static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }


}
