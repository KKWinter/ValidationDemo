package com.hldemo.demo;

import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.pub18828.core.api.SDK;
import com.pub18828.h5mob.H5API;

public class DemoApplication extends MultiDexApplication {

    public static final String APPID = "90002";//mp

    public static final String BANNER = "1033551";
    public static final String INTER = "1032725";
    public static final String NATIVE = "23052";
    public static final String APPWALL = "23058";
    public static final String VIDEO = "23035";


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        Stetho.initializeWithDefaults(this);

        com.pub18828.h5mob.H5API.setSDKMode(getApplicationContext(), H5API.SDK_MODE_CALL);

        SDK.initSDK(getApplicationContext(), APPID, "3fc7cbe75b45e7ce18b7f54b5edfda39");
    }
}
