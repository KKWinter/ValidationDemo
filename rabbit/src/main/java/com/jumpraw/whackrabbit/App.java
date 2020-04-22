package com.jumpraw.whackrabbit;

import android.app.Application;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.facebook.stetho.Stetho;

public class App extends Application {


    private static App sApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

//        initSDK("5054156", "android");


        initSDK("5054158", "music_fm_test");


//        initSDK("5001121", "APP Test Name");
        Stetho.initializeWithDefaults(sApplication);
    }

    public static App getApp() {
        return sApplication;
    }


    private void initSDK(String appID, String appName) {

        TTAdSdk.init(getApplicationContext(), new TTAdConfig.Builder()
                .appId(appID)
                .useTextureView(true) // Use TextureView to play the video. The default setting is SurfaceView, when the context is in conflict with SurfaceView, you can use TextureView
                .appName(appName)
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowPageWhenScreenLock(false) // Allow or deny permission to display the landing page ad in the lock screen
                .supportMultiProcess(false) // Whether to support multi-process, true indicates support
                .build());

    }
}
