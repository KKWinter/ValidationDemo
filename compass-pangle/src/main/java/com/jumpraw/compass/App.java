package com.jumpraw.compass;

import android.app.Application;
import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.facebook.stetho.Stetho;

public class App extends Application {

    public static Context context;

    public static String appID = "5057050";
    public static String appName = "Compass";

//    public static String appID = "5054156";
//    public static String appName = "rabbit";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        // It is strongly recommended to call in Application #onCreate () method, to avoid content as null
//        initSDK(appID, appName);

        Stetho.initializeWithDefaults(this);
    }


    private void initSDK(String appID, String appName) {

        TTAdSdk.init(getApplicationContext(), new TTAdConfig.Builder()
                .appId(appID)         //必需， Required parameter, set the app's AppId
                .appName(appName)     //必需， Required parameter, set the app name
                .useTextureView(true) // Use TextureView to play the video. The default setting is SurfaceView, when the context is in conflict with SurfaceView, you can use TextureView
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowPageWhenScreenLock(false) // Allow or deny permission to display the landing page ad in the lock screen
                .debug(true)   // Turn it on during the testing phase, you can troubleshoot with the log, remove it after launching the app
//                .supportMultiProcess(false) // Whether to support multi-process, true indicates support
//                .coppa(0)      //儿童政策 Fields to indicate whether you are a child or an adult ，0:adult ，1:child
//                .setGDPR(0)    //GDPR政策 Fields to indicate whether you are protected by GDPR,  the value of GDPR : 0 close GDRP Privacy protection ，1: open GDRP Privacy protection
                .build());


    }

}
