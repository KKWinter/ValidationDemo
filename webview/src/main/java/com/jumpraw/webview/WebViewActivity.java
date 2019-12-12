package com.jumpraw.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jumpraw.webview.sense.JRJSBridge;
import com.jumpraw.webview.sense.SenseVo;
import com.jumpraw.webview.sense.WebViewFeature;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * 页面加载webview，并模拟点击
 */
public class WebViewActivity extends Activity {

    private static final String TAG = "test";
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());

    private String clickStr1 = "https://www.92onegame.com/template_media.html?gameId=35&gameChannelId=193&gameTypeId=";
    private String clickStr2 = "http://game.gangofgames.club/";

    private int progress = 0;

    private String js1 = "(function() {\n" +
            "var t = document.getElementById('ad_top').offsetTop;\n" +
            "var l = document.getElementById('ad_top').offsetLeft;\n" +
            "var w = document.getElementById('ad_top').offsetWidth;\n" +
            "var h = document.getElementById('ad_top').offsetHeight;\n" +
            "var x = l + w / 2;\n" +
            "var y = t + h / 2;\n" +
            "jrJs.click_xy(x,y);\n" +
            "})()";

    private String js2 = "(function(i) {\n" +
            "var l = document.getElementsByClassName('lazy');\n" +
            "    if (l && l.length > 0) {\n" +
            "        if (l.length < i) {\n" +
            "            i = 0;\n" +
            "        }\n" +
            "        var top = l[i].offsetTop;\n" +
            "        var left = l[i].offsetLeft;\n" +
            "        var w = l[i].offsetWidth;\n" +
            "        var h = l[i].offsetHeight;\n" +
            "        var x = left + w / 2;\n" +
            "        var y = top + h / 2;\n" +
            "        jrJs.click_xy(x,y);\n" +
            "    }\n" +
            "})(4)";


    private String js3 = "(function() {\n" +
            "var t = document.getElementById('thum').offsetTop;\n" +
            "var l = document.getElementById('thum').offsetLeft;\n" +
            "var w = document.getElementById('thum').offsetWidth;\n" +
            "var h = document.getElementById('thum').offsetHeight;\n" +
            "var x = l + w / 2;\n" +
            "var y = t + h / 2;\n" +
            "jrJs.click_xy(x,y);\n" +
            "})()";


    private String js4 = "(function() {\n" +
            "    var ads = document.getElementsByClassName('lazy');\n" +
            "    var i = 0;\n" +
            "    var height = jrJs.obtain_h();\n" +
            "    for (i = 0; i < ads.length; i++) {\n" +
            "        if (ads[i].offsetTop >= height) {\n" +
            "            break;\n" +
            "        }\n" +
            "    }\n" +
            "    i--;\n" +
            "    var l = ads[i].offsetLeft;\n" +
            "    var t = ads[i].offsetTop;\n" +
            "    var w = ads[i].offsetWidth;\n" +
            "    var h = ads[i].offsetHeight;\n" +
            "    var x = l + w / 2;\n" +
            "    var y = t + h / 2;\n" +
            "    setTimeout(function(a, b) {\n" +
            "        jrJs.click_xy(a, b);\n" +
            "    }, 1000, x, y);\n" +
            "})()";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        context = this.getApplicationContext();


        final WebView webView = findViewById(R.id.wv);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SenseVo senseVo = new SenseVo();
                senseVo.url = clickStr2;
                senseVo.jsStrs = new String[]{js2, js3};

                //testWebview(context, webView);
                new WebViewFeature(context, senseVo).startLoadUrl(webView);
            }
        });


    }


    /**
     * 测试根据坐标点击的逻辑
     */
    private int[] testObtainXY(View view) {
        int[] result = new int[2];

        int width = view.getWidth();
        int height = view.getHeight();
        Log.i(TAG, "onCreate: 宽高 >>> " + width + ", >> " + height);

        //相对容器左上角的位置
        float x = view.getX();
        float y = view.getY();
        Log.i(TAG, "onCreate: 最外层坐标点 >>> " + x + ", >> " + y);

        //相对容器左上角的位置
        float left = view.getLeft();
        float top = view.getTop();
        Log.i(TAG, "onCreate: 左上角 >>> " + left + ", >> " + top);


        int[] position = new int[2];
        view.getLocationOnScreen(position);
        Log.i(TAG, "onCreate: 相对屏幕的左边点 >>> " + position[0] + ", >>>" + position[1]);

        int[] po = new int[2];
        view.getLocationInWindow(po);
        Log.i(TAG, "onCreate: 相对window的左边点 >>> " + po[0] + ", >>>" + po[1]);


        float tx = view.getTranslationX();
        float ty = view.getTranslationY();
        Log.i(TAG, "onCreate: 相对parent的左边点 >>> " + tx + ", >>>" + ty);


        result[0] = (int) (x + width / 2);
        result[1] = (int) (y + height / 2);

        return result;
    }


    /**
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
        webView.setAlpha(0);
        activity.addContentView(webView, lp);

//        initWebview(webView, activity, contentView, url);
    }


    public void testWebView(Context context, WebView webView) {

        Log.i(TAG, "testWebView: ");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        JRJSBridge.addToWebView(context, webView);


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i(TAG, "onConsoleMessage: >>>" + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading: ======= " + url);
                if (Utils.isDeepLinkUrl(url)) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                Log.i(TAG, "onPageFinished: ======= " + url);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                            view.loadUrl("javascript:" + js4);

                        } else {

                            view.evaluateJavascript("javascript:" + js4, new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {

                                    Log.i(TAG, "onReceiveValue: >>>" + value);

                                }
                            });
                        }


                    }
                }, 1000);

                super.onPageFinished(view, url);

            }

        });


        webView.loadUrl(clickStr2);
    }


    public void clickView(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 10;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

}
