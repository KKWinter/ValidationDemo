package com.phone.reflect;

import android.util.Log;

/**
 * Created by huangdong on 18/3/28.
 * antony.huang@yeahmobi.com
 */

public class SpecialFeature {

    public static final String TAG = "SpecialFeature";
    private String string = "1111111111";

    public SpecialFeature() {

    }

    private SpecialFeature(String string) {
        this.string = string;
    }


    private void printLog() {
        Log.i(TAG, "printLog: >>>>>>>>>>>" + string);
    }

    private void printLog(String string) {
        Log.i(TAG, "printLog: <<<<<<" + string);
    }


}
