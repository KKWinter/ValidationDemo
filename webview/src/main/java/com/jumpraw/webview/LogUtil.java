package com.jumpraw.webview;

import android.util.Log;

public class LogUtil {

    private static final String TAG = "LogUtil";

    //打印错误信息
    public static void printStackTrace(Throwable e) {
        Log.e(TAG, e.getMessage());
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

}
