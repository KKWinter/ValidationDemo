package com.jumpraw.compass;

import android.app.Application;
import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;

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
        TTAdSdk.init(context,
                new TTAdConfig.Builder()
                        .appId(appID)
                        .appName(appName)
                        .useTextureView(false) // Use TextureView to play the video. The default setting is SurfaceView, when the context is in conflict with SurfaceView, you can use TextureView
                        .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                        .allowShowPageWhenScreenLock(true) // Allow or deny permission to display the landing page ad in the lock screen
                        .debug(true) // Turn it on during the testing phase, you can troubleshoot with the log, remove it after launching the app
                        .supportMultiProcess(false) // Whether to support multi-process, true indicates support
                        .build());

    }

}
