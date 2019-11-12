package com.hldemo.demo.extra;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.hldemo.R;
import com.pub18828.adapters.admob.banner.AdMobBannerAdapter;
import com.pub18828.adapters.admob.interstitial.AdMobInterstitialAdapter;
import com.pub18828.adapters.admob.interstitial.InterstitialAdConfig;
import com.pub18828.adapters.admob.video.AdMobRewardedVideoAdapter;
import com.pub18828.adapters.admob.video.RewardedConfig;



public class AdmobAdManager {

//    final String APP_ID = "ca-app-pub-8409077524898984~3251047825";

    static final String BANNER_UNITID = "ca-app-pub-8409077524898984/6248494838";
    static final String INTERSTITIAL_UNITID = "ca-app-pub-8409077524898984/6313387092";
    static final String VIDEO_UNITID = "ca-app-pub-8409077524898984/8065452467";
    static final String NATIVE_UNITID = "ca-app-pub-8409077524898984/8173263671";

//    admob native
    static final String APP_ID = "ca-app-pub-8409077524898984~4509350837";

    boolean isShowMpAD = true;

    public AdmobAdManager() {
    }

    /**
     * -----------------------------------------admob banner ad----------------------------------------------
     **/

    AdView bannerAdView;
    AdRequest bannerAdRequest;

    public void initBannerAd(Activity activity, final ViewGroup container) {
        MobileAds.initialize(activity, APP_ID);
        bannerAdView = new AdView(activity);
        bannerAdView.setAdSize(AdSize.BANNER);
        bannerAdView.setAdUnitId(BANNER_UNITID);
        bannerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                System.out.println("------------onAdLoaded-----------------");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                System.out.println("------------onAdFailedToLoad-----------------" + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                System.out.println("------------onAdOpened-----------------");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                System.out.println("------------onAdLeftApplication-----------------");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                System.out.println("------------onAdClosed-----------------");
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        container.addView(bannerAdView, params);

    }

    /***
     * admob 加载mp广告
     */
    public void loadBannerAd() {
        if (bannerAdView != null) {
            if (isShowMpAD) {
                System.out.println("------------initAdRequest (mp)-----------------");
                //聚合MP
                Bundle customEventExtras = new Bundle();
                bannerAdRequest = new AdRequest.Builder()
                        .addCustomEventExtrasBundle(AdMobBannerAdapter.class, customEventExtras)
                        .build();

            } else {
                //原生广告
                bannerAdRequest = new AdRequest.Builder().build();
            }
            bannerAdView.loadAd(bannerAdRequest);
        }
    }

    /**
     * ----------------------------------------admob interstitial ad---------------------------------------------
     **/


    InterstitialAd mInterstitialAd;

    public void initInterstitialAd(final Activity activity) {
        MobileAds.initialize(activity, APP_ID);
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(INTERSTITIAL_UNITID);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(activity, "Admob interstitial ad load succes", Toast.LENGTH_SHORT).show();
                System.out.println("------------onAdLoaded-----------------");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(activity, "Admob interstitial ad load fail", Toast.LENGTH_SHORT).show();
                System.out.println("------------onAdFailedToLoad-----------------" + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                System.out.println("------------onAdOpened-----------------");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                System.out.println("------------onAdLeftApplication-----------------");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                System.out.println("------------onAdClosed-----------------");
            }
        });

    }

    /***
     * 广告类型
     * @param type  弹窗，全屏,半屏
     * @param UIConfig 自定义UL
     */
    public void loadInterstitialAd(int type,  InterstitialAdConfig UIConfig) {
        if (mInterstitialAd != null) {
            AdRequest adRequest;
            if (isShowMpAD) {
                System.out.println("------------initAdRequest (mp)-----------------");
                //聚合MP
                Bundle customEventExtras = new Bundle();
                customEventExtras.putInt(AdMobInterstitialAdapter.InterstitialAdRequestKey_Type, type);
                //自定义UI
                if(UIConfig != null) {
                    System.out.println("------------ (UIConfig)-----------------");
                    customEventExtras.putSerializable(AdMobInterstitialAdapter.InterstitialAdRequestKey_Config,UIConfig);
                    customEventExtras.setClassLoader(this.getClass().getClassLoader());
                }
                adRequest = new AdRequest.Builder()
                        .addCustomEventExtrasBundle(AdMobInterstitialAdapter.class, customEventExtras)
                        .build();
            } else {
                //原生广告
                adRequest = new AdRequest.Builder().build();
            }
            mInterstitialAd.loadAd(adRequest);
        }
    }

    /***
     * 广告展示
     * @param activity
     */
    public void showInterstitialAd(Activity activity) {
        if (mInterstitialAd != null) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Toast.makeText(activity, "admob interstitial ad not ready..", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * -----------------------------------------admob video ad----------------------------------------------
     **/

    private RewardedVideoAd mRewardedVideoAd;

    public void initAdmobVideo(final Activity activity) {

        MobileAds.initialize(activity, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(activity, "Admob video ad load success", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewardedVideoAdLoaded-----------------");
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Toast.makeText(activity, "Admob video ad open", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewardedVideoAdOpened-----------------");
            }

            @Override
            public void onRewardedVideoStarted() {
                Toast.makeText(activity, "Admob video ad start", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewardedVideoStarted-----------------");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Toast.makeText(activity, "Admob video ad close", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewardedVideoAdClosed-----------------");
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(activity, "Admob video ad reward", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewarded-----------------");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(activity, "Admob video ad left application", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewardedVideoAdLeftApplication-----------------");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(activity, "Admob video ad load fail", Toast.LENGTH_SHORT).show();
                System.out.println("------------onRewardedVideoAdFailedToLoad-----------------" + i);
            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });
    }

    RewardedConfig rewardedConfig;

    /***
     * admob 视频mp视频
     * @param type 横竖屏 自动适配
     */
    public void loadVideoAd(int type) {
        AdRequest adRequest;
        //初始化AdMob视频请求
        if (isShowMpAD) {
            System.out.println("------------initAdRequest-----------------");
            //聚合MP 视频广告配置
            rewardedConfig = new RewardedConfig();
            //是否静音 可选
            rewardedConfig.setSilent(true);
            //点击按钮颜色 可选
            rewardedConfig.setClickButtonColor(R.color.hartlion_videoad_test_g);
            //横竖屏模式 可选
            rewardedConfig.setmOrientation(type);
            //广告位 必填
//            rewardedConfig.setPlacementId(placementId);
            //用户信息  可选（需要回调的激励视频必选）
            rewardedConfig.setUserid("admob-test");
            Bundle customEventExtras = new Bundle();
            customEventExtras.putSerializable(AdMobRewardedVideoAdapter.ConfigurationExtraKey, rewardedConfig);
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobRewardedVideoAdapter.class, customEventExtras).build();

        } else {
            //原声Admob视频广告
            adRequest = new AdRequest.Builder().build();
        }
        if (!mRewardedVideoAd.isLoaded()) {
            System.out.println("------------loadGPRewardedVideoAd-----------------");
            //视频广告请求
            mRewardedVideoAd.loadAd(VIDEO_UNITID, adRequest);
        }
    }


    public void showVideoAd(Activity activity) {
        System.out.println("------------showGPRewardedVideo-------------");
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            Toast.makeText(activity, "Admob Video is not ready!!!! ", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isVideoReady() {
        return mRewardedVideoAd.isLoaded();
    }

    /**
     * -----------------------------------------div----------------------------------------------
     **/

    public void destory() {
        if (bannerAdView != null) {
            bannerAdView.destroy();
        }

    }





}
