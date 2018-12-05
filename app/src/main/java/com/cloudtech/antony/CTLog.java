package com.cloudtech.antony;

import android.util.Log;

/**
 * Created by huangdong on 18/8/22.
 * antony.huang@yeahmobi.com
 */
public class CTLog {

    private static final String TAG = "Accessibility";

    public static void d(String string) {

        Log.d(TAG, "d: " + string);
    }

    public static void i(String string) {

        Log.i(TAG, "d: " + string);
    }

    public static void d(String TAG, String msg) {
        Log.d(TAG, "d: " + msg);
    }

    public static void i(String TAG, String msg) {
        Log.i(TAG, "d: " + msg);
    }

}
