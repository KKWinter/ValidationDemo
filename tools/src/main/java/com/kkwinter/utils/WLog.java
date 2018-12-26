package com.kkwinter.utils;

import android.text.TextUtils;
import android.util.Log;

import com.kkwinter.utils.config.Const;

public class WLog {

    private final static String TAG = "CTService_";

    private WLog() {

    }

    private static String getLogTag(String tag) {
        if (tag == null) {
            return TAG;
        }
        return TAG + tag;
    }

    //不受开关控制
    public static void info(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Log.i(TAG, msg);
        }
    }

    //打印错误信息
    public static void printStackTrace(Throwable ex) {
        if (Const.LOG) {
            if (ex != null) {
                ex.printStackTrace();
            }
        }
    }

    public static void i(String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.i(TAG, msg);
            }
        }
    }

    public static void d(String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.d(TAG, msg);
            }
        }
    }

    public static void w(String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.w(TAG, msg);
            }
        }
    }

    public static void e(String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.e(TAG, msg);
            }
        }
    }


    public static void i(String tag, String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.i(getLogTag(tag), msg);
            }
        }
    }


    public static void d(String tag, String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.d(getLogTag(tag), msg);
            }
        }
    }


    public static void w(String tag, String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.w(getLogTag(tag), msg);
            }
        }
    }


    public static void e(String tag, String msg) {
        if (Const.LOG) {
            if (!TextUtils.isEmpty(msg)) {
                Log.e(getLogTag(tag), msg);
            }
        }
    }

}
