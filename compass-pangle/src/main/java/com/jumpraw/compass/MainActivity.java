package com.jumpraw.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "pangle";

    public static String appID = "5057050";
    public static String appName = "Compass";

//    public static String appID = "5054156";
//    public static String appName = "rabbit";

    //compass
    public static String interstitial = "945109241";
    public static String rewardvideo = "945126258";

    //rabbit
//    public static String interstitial = "945105375";
//    public static String rewardvideo = "945105337";

    //40021 : apk签名SHA1值与媒体平台录入的SHA1不一致

    private Context context;
    private TTAdNative mTTAdNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sha1 = AppSigning.getSha1(context);

                Log.i(TAG, "onClick: >>>" + sha1);

                hookPackageName(context, "com.jumpraw.whackrabbit");
            }
        });

        findViewById(R.id.bt0).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: 2020/4/24 目标是在此compass内用rabbit的信息也可以请求到广告

                //改系统的会crash，所以要hook穿山甲获取包名并上报的方法

                String packageName = context.getPackageName();
                Log.i(TAG, "onClick: >>>" + packageName);

                hook(context);

            }
        });


        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadInterstitial();
                initSDK(appID, appName);
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Must be called after initialization, otherwise it will be null
                TTAdManager ttAdManager = TTAdSdk.getAdManager();
                ttAdManager.requestPermissionIfNecessary(context);


                //baseContext is suggested to be activity
                mTTAdNative = ttAdManager.createAdNative(context);


                loadRewarded();
            }
        });

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

    private void loadInterstitial() {
        // Set the ad parameters
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(interstitial)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();

        // Load full-screen video ad
        mTTAdNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.i(TAG, "onError: >>" + code + ", >> " + message);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                Log.i(TAG, "onFullScreenVideoAdLoad: ");
                TTFullScreenVideoAd mttFullVideoAd = ad;
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "onAdShow: ");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.i(TAG, "onAdVideoBarClick: ");
                    }

                    @Override
                    public void onAdClose() {
                        Log.i(TAG, "onAdClose: ");
                    }

                    @Override
                    public void onVideoComplete() {
                        Log.i(TAG, "onVideoComplete: ");
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.i(TAG, "onSkippedVideo: ");
                    }

                });

                mttFullVideoAd.showFullScreenVideoAd(MainActivity.this);
            }

            @Override
            public void onFullScreenVideoCached() {
                Log.i(TAG, "onFullScreenVideoCached: ");
            }
        });
    }


    private void loadRewarded() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(rewardvideo)
                .setSupportDeepLink(true)
                .setAdCount(2)
                .setImageAcceptedSize(1080, 1920)
                .setRewardName("gold coin") //name of the reward
                .setRewardAmount(3) // number of rewards
                // It is developer's unique identifier for users; sdk pass-through is not necessary if the server is not in callback mode
                // can be set to an empty string
                .setUserID("user123")
                .setOrientation(TTAdConstant.VERTICAL) // Set how you wish the video ad to be displayed, choose from TTAdConstant.HORIZONTAL or TTAdConstant.VERTICAL
                .setMediaExtra("media_extra") // pass-through user information, not mandatory
                .build();

        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.i(TAG, "onError: >>>" + code + ", == " + message);
            }

            //The loaded video file is cached to the local callback
            @Override
            public void onRewardVideoCached() {
                Log.i(TAG, "onRewardVideoCached: ");
            }

            //Video creatives are loaded into, such as title, video url, etc., excluding video files
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.i(TAG, "onRewardVideoAdLoad: ");
                TTRewardVideoAd mttRewardVideoAd = ad;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "onAdShow: ");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.i(TAG, "onAdVideoBarClick: ");
                    }

                    @Override
                    public void onAdClose() {
                        Log.i(TAG, "onAdClose: ");
                    }

                    @Override
                    public void onVideoComplete() {
                        Log.i(TAG, "onVideoComplete: ");
                    }

                    @Override
                    public void onVideoError() {
                        Log.i(TAG, "onVideoError: ");
                    }

                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Toast.makeText(MainActivity.this, "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.i(TAG, "onSkippedVideo: ");
                    }
                });


                mttRewardVideoAd.showRewardVideoAd(MainActivity.this);
            }
        });
    }


    public static void hookPackageName(Context context, String packageName) {

        try {
            Class activityThreadCls = Class.forName("android.app.ActivityThread");

            Method catMethod = activityThreadCls.getDeclaredMethod("currentActivityThread");
            catMethod.setAccessible(true);
            Object activityThreadObj = catMethod.invoke(null);


            Field mbaField = activityThreadCls.getDeclaredField("mBoundApplication");
            mbaField.setAccessible(true);
            Object appBindDataObj = mbaField.get(activityThreadObj);

            Class appBindDataCls = Class.forName("android.app.ActivityThread$AppBindData");
            Field infoField = appBindDataCls.getDeclaredField("info");
            infoField.setAccessible(true);
            Object infoObj = infoField.get(appBindDataObj);


            Class apkCls = Class.forName("android.app.LoadedApk");
            Field pkgName = apkCls.getDeclaredField("mPackageName");
            pkgName.setAccessible(true);
            pkgName.set(infoObj, packageName);


            Class implCls = Class.forName("android.app.ContextImpl");
            Method getImplMethod = implCls.getDeclaredMethod("getImpl", Context.class);
            getImplMethod.setAccessible(true);
            Object implObj = getImplMethod.invoke(null, context);

            Field infoField2 = implCls.getDeclaredField("mPackageInfo");
            infoField2.setAccessible(true);
            infoField2.set(implObj, infoObj);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


//        Field appInfoField = appBindDataCls.getDeclaredField("appInfo");
//        appInfoField.setAccessible(true);
//        Object appInfoObj = appInfoField.get(appBindDataObj);


//        Class itemInfoCls = Class.forName("android.content.pm.PackageItemInfo");
//        Field pkgField = itemInfoCls.getDeclaredField("packageName");
//        String packageName = (String) pkgField.get(appInfoObj);
//        Log.i(TAG, "hookPackageName: >>>>>>>" + packageName);
//
//        pkgField.set(appInfoObj, "com.jumpraw.whackrabbit");
//
//
//        String packageName1 = (String) pkgField.get(appInfoObj);
//        Log.i(TAG, "hookPackageName: >>>>>>>======" + packageName1);
    }


    public void hook(Context context) {

        try {
            Class cls = Class.forName("com.bytedance.embedapplog.util.i");
            Field field = cls.getDeclaredField("a");
            field.setAccessible(true);
            field.set(null, "com.jumpraw.whackrabbit");

            Method method = cls.getDeclaredMethod("a", Context.class);
            method.setAccessible(true);
            String result = (String) method.invoke(null, context);
            Log.i(TAG, "hook: >>>" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
