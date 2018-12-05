package com.phone.adlibrary;

import android.support.annotation.Keep;
import android.util.Log;

/**
 * Created by huangdong on 2018/10/30.
 * antony.huang@yeahmobi.com
 */
@Keep
public class TestInner {

    private static final String TAG = "TestInner";

    @Keep
    public interface Listener {

        void loadSuccess();

        void loadFailed();
    }


    public static void loadDex() {

        Log.i(TAG, "loadDex: >>>>>>>>>>");

    }

}
