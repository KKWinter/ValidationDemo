package com.jumpraw.webview.sense;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JRJSBridge {

    private static final String DEFAULT_NAME = "jrJs";

    @SuppressLint("JavascriptInterface")
    public static void addToWebView(Context context, WebView webView) {
        webView.addJavascriptInterface(new JRJSBridge(context, webView), DEFAULT_NAME);
    }

    private Context context;
    private WebView webView;

    public JRJSBridge(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    //jrJs.obtain_w()
    @JavascriptInterface
    public int obtain_w() {

        return px2dip(context, webView.getWidth());
    }

    //jrJs.obtain_h()
    @JavascriptInterface
    public int obtain_h() {

        return px2dip(context, webView.getHeight());
    }

    @JavascriptInterface
    public void click_xy(int x, int y) {

        Log.i("click_xy", ">> x: " + x + " -- y: " + y);
        simulateTouchEvent(webView, dip2px(context, x), dip2px(context, y));
    }

    //模拟点击
    private void simulateTouchEvent(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
        view.dispatchTouchEvent(motionEvent);

        MotionEvent upEvent = MotionEvent.obtain(downTime + 100, eventTime + 100, MotionEvent.ACTION_UP, x, y, 0);
        view.dispatchTouchEvent(upEvent);
    }

    /**
     * dip转像素px
     */
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素px转dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
