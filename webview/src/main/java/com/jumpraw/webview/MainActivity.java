package com.jumpraw.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * webview 中加载http/https url，无法通过javascript实现跨域
 * <p>
 * webview 中做模拟广告点击
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

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
//        checkPermission();

        webView = findViewById(R.id.webview);

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
        webView.addJavascriptInterface(new JumpRawJSBridge(), "jumpraw");


//        webView.loadUrl(url);
        webView.loadUrl("file:///android_asset/bridge");
//        String htmlStr = getAssets(context, "bridge");
//        webView.loadData(htmlStr, "text/html", "utf-8");
//        webView.loadDataWithBaseURL("http://www.jcodecraeer.com", htmlStr, "text/html", "utf-8",null);


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取页面内容
                // webView.loadUrl("javascript:window.jumpraw.showSource('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

                // webView.loadUrl("javascript:window.android.printStatus(document.readyState)");
            }
        });


        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void loadJS() {
        Log.i("test", "loadJS: >>" + jsStr);

        if (Build.VERSION.SDK_INT <= 18) {
            webView.loadUrl("javascript:" + jsStr);

        } else {
            webView.evaluateJavascript("javascript:" + jsStr, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

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
        } else {
            // app不存在
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
//        injectWebView(this);
    }



    /*
     * 向当前页面插入webview，加载url之后，并模拟点击
     */

    private void injectWebView(Activity activity) {
        FrameLayout contentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup viewGroup = (ViewGroup) contentView.getChildAt(0);


        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels);

        lp.gravity = Gravity.BOTTOM;
        lp.setMargins(dm.widthPixels - 100, 0, 0, 0);

        WebView webView = new WebView(activity.getApplicationContext());
//            webView.setAlpha(0);
        activity.addContentView(webView, lp);
        initWebview(webView, activity, contentView, url);




//            if (viewGroup instanceof LinearLayout){

//                PreferUtil.persist(mContext, Const.Clic_COMPLETE,0);
//                DisplayMetrics dm = new DisplayMetrics();
//                activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        dm.widthPixels, dm.heightPixels);
//                lp.gravity = Gravity.BOTTOM;
//                lp.setMargins(dm.widthPixels,0,0,0);
//                WebView webView=new WebView(activity.getApplicationContext());
//                webView.setAlpha(0);
//                activity.addContentView(webView,lp);
//                initWebview(webView,activity,contentView, (String) mMessage.obj);
//            }else if(viewGroup instanceof FrameLayout){
//                if (!enterSetWebView.compareAndSet(false, true)) {
//                    return;
//                }
//                DisplayMetrics dm = new DisplayMetrics();
//                activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        dm.widthPixels, dm.heightPixels);
//                lp.gravity = Gravity.BOTTOM;
//                lp.setMargins(dm.widthPixels,0,0,0);
//                WebView webView=new WebView(activity.getApplicationContext());
//                webView.setAlpha(0);
//                activity.addContentView(webView,lp);
//                initWebview(webView,activity,contentView, (String) mMessage.obj);
//            }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private static void initWebview(final WebView webView, final Activity activity, final FrameLayout frameLayout, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                final int[] location = new int[2];
                view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                Log.i(TAG, " lax: " + location[0]);
                Log.i(TAG, " lay: " + location[1]);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        DisplayMetrics dm = new DisplayMetrics();
                        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                        Log.i(TAG, "run: w " + dm.widthPixels + "  h " + dm.heightPixels);
                        //应用区域
                        Rect outRect1 = new Rect();
                        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
                        int y = location[1] - outRect1.top;
                        Log.i(TAG, "run: " + y);
                        simulateTouchEvent(webView, webView.getWidth() / 2, (dm.heightPixels - y) * 0.423f + location[1]);
                    }
                }, 1000);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i(TAG, "onProgress:" + newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i(TAG, "Title: " + title);
            }
        });

//        webView.setAlpha(0);
        webView.loadUrl(url);
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("webview", "onTouch: >>> after click");
//                PreferUtil.persist(mContext, Const.Clic_COMPLETE, 1);
//                frameLayout.removeView(v);
//                return false;
//            }
//        });
    }

    private static void simulateTouchEvent(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, metaState);
        view.dispatchTouchEvent(motionEvent);

        MotionEvent upEvent = MotionEvent.obtain(downTime + 100, eventTime + 100,
                MotionEvent.ACTION_UP, x, y, metaState);
        view.dispatchTouchEvent(upEvent);

    }

}
