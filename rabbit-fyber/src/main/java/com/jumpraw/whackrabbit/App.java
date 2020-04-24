package com.jumpraw.whackrabbit;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * 对接fyber测试，通过fbyer聚合各个network验证
 */
public class App extends Application {


    private static App sApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        Stetho.initializeWithDefaults(sApplication);

    }

    public static App getApp() {
        return sApplication;
    }



}
