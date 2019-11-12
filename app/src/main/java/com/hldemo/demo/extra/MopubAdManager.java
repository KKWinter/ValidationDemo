package com.hldemo.demo.extra;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pub18828.adapters.mopub.video.VideoAdapter;
import com.pub18828.component.banner.api.BannerConfig;
import com.pub18828.component.interstitial.api.InterstitialConfig;
import com.hldemo.demo.mopub.HLAdRenderer;
import com.hldemo.demo.mopub.HLViewBinder;
import com.pub18828.video.api.VideoConfig;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideoManager;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.RequestParameters;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;


public class MopubAdManager {

    final String BANNER_UNITID = "5709dc3c1b254834b1935f5e34a28e64";
    final String INTERSTITIAL_UNITID = "c31f7ecd495a4046ae5f30e4a941483a";
    final String NATIVE_UNITID = "1a668cdd9e064d2bbe63c0e608d9bfd6";
    final String VIDEO_UNITID = "98754bfc0c974c248875821a5015ed4a";

    public MopubAdManager() {
    }

    /**-----------------------------------------mopub banner ad----------------------------------------------**/

    /**
     * mopub banner
     */
    MoPubView mMopubView;

    public void initMopubBannerView(Activity activity, ViewGroup contentView) {

        mMopubView = new MoPubView(activity);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        contentView.addView(mMopubView, params);

        mMopubView.setAdUnitId(BANNER_UNITID);
        BannerConfig bannerConfig = new BannerConfig();
        bannerConfig.setAdChoiceSwitch(1);
        bannerConfig.setCloseButtonSwitch(0);

        HashMap<String, Object> configMaps = new HashMap<>();
        configMaps.put("config", bannerConfig);
        mMopubView.setLocalExtras(configMaps);
        mMopubView.setBannerAdListener(new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(MoPubView banner) {
                Log.i("BannerDemo", "banner mopub: onBannerLoaded");
            }

            @Override
            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                Log.i("BannerDemo", "banner mopub: onBannerFailed");
            }

            @Override
            public void onBannerClicked(MoPubView banner) {
                Log.i("BannerDemo", "banner mopub: onBannerClicked");
            }

            @Override
            public void onBannerExpanded(MoPubView banner) {
                Log.i("BannerDemo", "banner mopub: onBannerExpanded");
            }

            @Override
            public void onBannerCollapsed(MoPubView banner) {
                Log.i("BannerDemo", "banner mopub: onBannerCollapsed");
            }
        });
    }

    public void loadBannerAd() {
        if (mMopubView != null) {
            mMopubView.loadAd();
        }
    }
    /**----------------------------------------mopub interstitial ad---------------------------------------------**/


    MoPubInterstitial mInterstitial;
    public void initInterstitialMopub(final Activity activity) {
        mInterstitial = new MoPubInterstitial(activity, INTERSTITIAL_UNITID);

        mInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {

            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                Log.i("InterstitialDemo", "onInterstitialLoaded-------");
                Toast.makeText(activity, "interstitial ad load success", 300).show();
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                Log.i("InterstitialDemo", "onInterstitialFailed-------");
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {
                Log.i("InterstitialDemo", "onInterstitialShown-------");
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {
                Log.i("InterstitialDemo", "onInterstitialClicked-------");
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                Log.i("InterstitialDemo", "onInterstitialDismissed-------");
            }
        });
    }

    public void loadInterstitialAd(int type) {
        if (mInterstitial != null) {
            HashMap<String, Object> maps = new HashMap<String,Object>();
            maps.put("Type", type);
            InterstitialConfig interstitialConfig = new InterstitialConfig();
            interstitialConfig.setAdChoiceSwitch(1);

            maps.put("config", interstitialConfig);
            mInterstitial.setLocalExtras(maps);
            mInterstitial.load();
        }
    }

    public void showInterstitialAd(Activity activity) {
        if (mInterstitial != null) {
            if (mInterstitial.isReady()) {
                mInterstitial.show();
            } else {
                Toast.makeText(activity, "interstitial ad not ready..", 300).show();
            }
        }
    }

    /**-----------------------------------------mopub native ad----------------------------------------------**/

    /**
     * mopub集成代码
     */
    MoPubNative moPubNative;
    RequestParameters myRequestParameters;
    View mNativeAdView;

    public void initMopubNativeAd(final Activity activity, final ViewGroup container, int layoutId, int imageId, int titleId, int descId, int ctaId) {
        HLViewBinder mViewBinder = new HLViewBinder.Builder(layoutId)
                .iconImageId(imageId)
                .titleId(titleId)
                .textId(descId)
                .callToActionId(ctaId)
                .build();


        final HLAdRenderer adRenderer = new HLAdRenderer(mViewBinder);

        final EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                RequestParameters.NativeAdAsset.TITLE,
                RequestParameters.NativeAdAsset.TEXT,
                // Don't pull the ICON_IMAGE
                // NativeAdAsset.ICON_IMAGE,
                RequestParameters.NativeAdAsset.MAIN_IMAGE,
                RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT);
//
//
        myRequestParameters = new RequestParameters.Builder()
                .desiredAssets(desiredAssets)
                .build();

        moPubNative = new MoPubNative(activity, NATIVE_UNITID, new MoPubNative.MoPubNativeNetworkListener() {
            /**
             * mopub回调
             * @param nativeAd
             */
            @Override
            public void onNativeLoad(NativeAd nativeAd) {
                Log.e("mopubNative", "========nor====LOADED");
                View adWrapper = nativeAd.createAdView(activity, container);

                nativeAd.setMoPubNativeEventListener(new NativeAd.MoPubNativeEventListener() {
                    @Override
                    public void onImpression(View view) {
                        Log.e("mopubNative", "========nor====onImpression");
                    }

                    @Override
                    public void onClick(View view) {
                        Log.e("mopubNative", "========nor====onClick");
                    }

                });

                if (mNativeAdView != null) {
                    container.removeView(mNativeAdView);
                }
                mNativeAdView = adWrapper;
                nativeAd.renderAdView(adWrapper);
                container.addView(adWrapper);

            }

            @Override
            public void onNativeFail(NativeErrorCode errorCode) {
                Log.e("mopubNative", "========onNativeFail：" + errorCode.name());
            }
        });

        moPubNative.registerAdRenderer(adRenderer);
    }

    public void loadNativeAd() {
        if (moPubNative != null) {
            moPubNative.makeRequest(myRequestParameters);
        }
    }

    /**-----------------------------------------mopub video ad----------------------------------------------**/

    /**
     * mopub初始化
     */
    MoPubRewardedVideoListener rewardedVideoListener;
    public void initMopubVideo(final Activity activity) {
        VideoConfig config = new VideoConfig();
        MoPubRewardedVideos.initializeRewardedVideo(activity, new VideoAdapter.HLMediationSettings(config));
        MoPubRewardedVideoManager.updateActivity(activity);
        rewardedVideoListener = new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(String adUnitId) {
                Toast.makeText(activity,"mopub video load sucess",Toast.LENGTH_LONG).show();
                // Called when the adUnitId has loaded. At this point you should be able to call MoPub.showRewardedVideo(String) to show the video
            }

            @Override
            public void onRewardedVideoLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                Toast.makeText(activity,"mopub video load failure "+errorCode,Toast.LENGTH_LONG).show();
                // Called when a video fails to load for the given ad unit id. The provided error code will provide more insight into the reason for the failure to load.
            }

            @Override
            public void onRewardedVideoStarted(String adUnitId) {
                // Called when a rewarded video starts playing.
                Toast.makeText(activity,"mopub video video start",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedVideoPlaybackError(String adUnitId, MoPubErrorCode errorCode) {
                Toast.makeText(activity,"mopub video play error",Toast.LENGTH_LONG).show();
                //  Called when there is an error during video playback.
            }

//            @Override
//            public void onRewardedVideoClicked(@NonNull String adUnitId) {
//
//            }


            @Override
            public void onRewardedVideoClosed(String adUnitId) {
                Toast.makeText(activity,"mopub video video close",Toast.LENGTH_LONG).show();
                // Called when a rewarded video is closed. At this point your application should resume.
            }

            @Override
            public void onRewardedVideoCompleted(Set adUnitIds, MoPubReward reward) {
                Toast.makeText(activity,"mopub video play completed",Toast.LENGTH_LONG).show();
                // Called when a rewarded video is completed and the user should be rewarded.
                // You can query the reward object with boolean isSuccessful(), String getLabel(), and int getAmount().
            }

        };
        MoPubRewardedVideos.setRewardedVideoListener(rewardedVideoListener);
    }

    public void loadVideoAd(int type){
        VideoConfig config = new VideoConfig();
        config.setmOrientation(type);
        VideoAdapter.HLMediationSettings mediationSettings = new VideoAdapter.HLMediationSettings(config);

        MoPubRewardedVideos.loadRewardedVideo(VIDEO_UNITID
                , new MoPubRewardedVideoManager.RequestParameters("keywords", null,
                        "test-user"), mediationSettings);
    }

    public void showVideoAd(Activity activity) {
        if(MoPubRewardedVideos.hasRewardedVideo(VIDEO_UNITID)){
            MoPubRewardedVideos.showRewardedVideo(VIDEO_UNITID);
        } else {
            Toast.makeText(activity, "Mopub Video is not ready!!!! ", 300).show();
        }
    }

    public boolean isVideoReady() {
        return MoPubRewardedVideos.hasRewardedVideo(VIDEO_UNITID);
    }

    /**-----------------------------------------div----------------------------------------------**/

    public void destory() {
        if (mMopubView != null) {
            mMopubView.destroy();
        }
        if(mInterstitial != null){
            mInterstitial.destroy();
        }
    }
}
