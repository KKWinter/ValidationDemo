package com.jumpraw.whackrabbit;

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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "pangle";
    public static String appID = "5057050";
    public static String appName = "Compass";
    public static String interstitial = "945109241";
    public static String rewardvideo = "945126258";
    private Context context;
    private TTAdNative mTTAdNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        findViewById(R.id.bt0).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Must be called after initialization, otherwise it will be null
                TTAdManager ttAdManager = TTAdSdk.getAdManager();
                ttAdManager.requestPermissionIfNecessary(context);
                //baseContext is suggested to be activity
                mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);

            }
        });


        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

    }
}
