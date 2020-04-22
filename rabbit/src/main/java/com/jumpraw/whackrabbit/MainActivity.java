package com.jumpraw.whackrabbit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
//import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd.RewardAdInteractionListener;

import java.lang.annotation.Native;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TTAdNative mTTAdNative;
    private TTFullScreenVideoAd mttFullVideoAd;
    private TTRewardVideoAd mttRewardVideoAd;
    private Context context;
    private RelativeLayout container;

    //rabbit
//    private String interstitial = "945105375";
//    private String rewardvideo = "945105337";


    //music-fm-test
    private String interstitial = "945085769";
    private String rewardvideo = "945085780";


    private String bigNative = "945085785";  // 大图，宽高比1.78，最低尺寸690*388px   // imagemode = 3
    //实际获取，mode = 3， width 1280 ，height 720

    private String videoNative = "945143430";  //视频，视频分辨率1280*720            //imagemode=5


    private String smallNative = "945143431";  //小图， 宽高比1：1， 最低尺寸640*640px   //imagemode = 2
    //实际获取，mode=2， width 1094 ，height 720

    //pangle-demo
    private String demoid = "945071429";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        TTAdManager ttAdManager = TTAdSdk.getAdManager();
        ttAdManager.requestPermissionIfNecessary(context);
        mTTAdNative = ttAdManager.createAdNative(this);


        container = findViewById(R.id.container);
        findViewById(R.id.bt0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                loadBannerAd("945111979");

//                loadNativeAd(videoNative);

//                loadExpressNativeAd(bigNative);

                loadFeedAd(smallNative);
            }

        });


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFullScreenVideoAd(interstitial);  //whackrabbit

            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadRewardVideoAd(rewardvideo);

            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sha1 = AppSigning.getSha1(getApplicationContext());
                Log.i(TAG, "SHA1: >>>>" + sha1);
//
//
//                String packageName = context.getPackageName();
//                Log.i(TAG, "packageName: >>>>" + packageName);
//
//
//                try {
//                    String result = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).packageName;
//                    Log.i(TAG, "another way: =========" + result);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }

            }
        });
    }


//    private void loadBannerAd(String slotID) {
//
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(slotID)
//
//                .setAdCount(1)
//                .setSupportDeepLink(true)
//                .build();
//
//        mTTAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {
//            @Override
//            public void onError(int i, String s) {
//                Log.i(TAG, "onError: >>" + i + ", msg: " + s);
//            }
//
//            @Override
//            public void onBannerAdLoad(TTBannerAd ttBannerAd) {
//                View bannerView = ttBannerAd.getBannerView();
//                ttBannerAd.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
//                    @Override
//                    public void onAdClicked(View view, int i) {
//                        Log.i(TAG, "onAdClicked: >>>>>>");
//                    }
//
//                    @Override
//                    public void onAdShow(View view, int i) {
//                        Log.i(TAG, "onAdShow: ============");
//                    }
//                });
//
//                if (container != null) {
//                    container.removeAllViews();
//                    container.addView(bannerView);
//                }
//            }
//        });
//
//    }


//    private void loadExpressNativeAd(String slotID) {
//
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(slotID) //广告位id
//                .setSupportDeepLink(true)
//                .setAdCount(1) //请求广告数量为1到3条
//                .setExpressViewAcceptedSize(300, 100) //期望个性化模板广告view的size,单位dp
//                .setImageAcceptedSize(640, 320) //这个参数设置即可，不影响个性化模板广告的size
//                .build();
//
//        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                container.removeAllViews();
//            }
//
//            @Override
//            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
//                if (ads == null || ads.size() == 0) {
//                    return;
//                }
//                TTNativeExpressAd mTTAd = ads.get(0);
////                bindAdListener(mTTAd);
//
//
//                mTTAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
//                    @Override
//                    public void onAdClicked(View view, int type) {
//
//                        Log.i(TAG, "onAdClicked: ");
//                    }
//
//                    @Override
//                    public void onAdShow(View view, int type) {
//                        Log.i(TAG, "onAdShow: ");
//                    }
//
//                    @Override
//                    public void onRenderFail(View view, String msg, int code) {
//                        Log.i(TAG, "onRenderFail: ");
//                    }
//
//                    @Override
//                    public void onRenderSuccess(View view, float width, float height) {
//                        //返回view的宽高 单位 dp
//
//                        if (container != null) {
//                            container.removeAllViews();
//                            container.addView(view);
//                        }
//
//                    }
//                });
//
//
//                mTTAd.render();//调用render开始渲染广告
//            }
//        });
//
//
//    }


//    private void loadNativeAd(String slotID) {
//
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(slotID)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(1080, 1920)
//                //When requesting a native ad,
//                // be sure to call this method and set the parameter to
//                // TYPE_BANNER or TYPE_INTERACTION_AD
//                .setNativeAdType(AdSlot.TYPE_INTERACTION_AD)
//                .build();
//
//        mTTAdNative.loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
//            @Override
//            public void onError(int i, String s) {
//                Log.i(TAG, "onError: >>" + i + ", msg: " + s);
//            }
//
//            @Override
//            public void onNativeAdLoad(List<TTNativeAd> list) {
//                if (list.get(0) == null) {
//                    return;
//                }
//
//                TTNativeAd ad = list.get(0);
//
//                TextView textView = getTextView(context, ad.getTitle());
//                ad.registerViewForInteraction(container, textView, new TTNativeAd.AdInteractionListener() {
//                    @Override
//                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {
//                        Log.i(TAG, "onAdClicked: >>>>>>>>>");
//                    }
//
//                    @Override
//                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
//                        Log.i(TAG, "onAdCreativeClick: ============");
//                    }
//
//                    @Override
//                    public void onAdShow(TTNativeAd ttNativeAd) {
//                        Log.i(TAG, "onAdShow: ================");
//                    }
//                });
//
//
//                if (container != null) {
//                    container.removeAllViews();
//                    container.addView(textView);
//                }
//
//            }
//        });
//
//    }

//    private TextView getTextView(Context context, String content) {
//
//        TextView textView = new TextView(context);
//        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        textView.setGravity(Gravity.CENTER);
//        textView.setText(content);
//        textView.setTextSize(30);
//        textView.setTextColor(Color.parseColor("#000000"));
//
//        return textView;
//    }


    private void loadFeedAd(String slotID) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID)
                .setImageAcceptedSize(1080, 1920)
                .build();

        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                //For failed loading callbacks, please refer to "Error Code Description"
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onError: >>" + code + ">>" + message);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                //For successful loading callbacks, please make sure your code is robust enough to overcome exceptions.
                if (ads == null || ads.isEmpty()) {
                    Toast.makeText(MainActivity.this, "on FeedAdLoaded: ad is null!", Toast.LENGTH_SHORT).show();
                    return;
                }

                TTFeedAd ad = ads.get(0);
                Log.i(TAG, "onFeedAdLoad: >>>imagemode>>" + ad.getImageMode());

//                View view = ad.getAdView();
//
//                ad.setVideoAdListener(new TTFeedAd.VideoAdListener() {
//                    @Override
//                    public void onVideoLoad(TTFeedAd ttFeedAd) {
//
//                    }
//
//                    @Override
//                    public void onVideoError(int i, int i1) {
//
//                    }
//
//                    @Override
//                    public void onVideoAdStartPlay(TTFeedAd ttFeedAd) {
//
//                    }
//
//                    @Override
//                    public void onVideoAdPaused(TTFeedAd ttFeedAd) {
//
//                    }
//
//                    @Override
//                    public void onVideoAdContinuePlay(TTFeedAd ttFeedAd) {
//
//                    }
//
//                    @Override
//                    public void onProgressUpdate(long l, long l1) {
//
//                        Log.i(TAG, "onProgressUpdate: >>>" + l + "====" + l1);
//                    }
//
//                    @Override
//                    public void onVideoAdComplete(TTFeedAd ttFeedAd) {
//
//                    }
//                });

                View view = View.inflate(context, R.layout.layout_small, null);
                ImageView image = view.findViewById(R.id.iv_icon);
                TextView title = view.findViewById(R.id.tv_title);
                TextView des = view.findViewById(R.id.tv_des);

                TTImage ttImage = ad.getImageList().get(0);
                Log.i(TAG, "onFeedAdLoad: >>>" + ad.getImageList().size());
                Log.i(TAG, "onFeedAdLoad: >>>" + ttImage.getHeight() + ">>" + ttImage.getWidth());
                Log.i(TAG, "onFeedAdLoad: ===" + ttImage.getImageUrl());
                ttImage.getImageUrl();

                Glide.with(context).load(ttImage.getImageUrl()).into(image);
                title.setText(ad.getTitle());
                des.setText(ad.getDescription());

                List<View> clickView = new ArrayList<>();
                clickView.add(image);
                List<View> createView = new ArrayList<>();
                createView.add(title);
                createView.add(des);

                ad.registerViewForInteraction(container, view, new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {

                        Log.i(TAG, "onAdClicked: >>>>>>>>");
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {

                        Log.i(TAG, "onAdCreativeClick: >>>>>>>>");
                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {

                        Log.i(TAG, "onAdShow: >>>>>>>>>>");
                    }
                });

                if (container != null) {
                    container.removeAllViews();
                    container.addView(view);
                }

            }


        });

    }


    private void loadFullScreenVideoAd(String slotID) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();

        mTTAdNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.i(TAG, "code: " + code + ", msg: " + message);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                Log.i(TAG, "onFullScreenVideoAdLoad: ");

                mttFullVideoAd = ad;
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "onFullScreenVideoonAdShow: " + System.currentTimeMillis());
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

                if (mttFullVideoAd != null) {
                    mttFullVideoAd.showFullScreenVideoAd(MainActivity.this);
                }
            }

            @Override
            public void onFullScreenVideoCached() {
                Log.i(TAG, "onFullScreenVideoCached: ");

            }
        });


    }


    private void loadRewardVideoAd(String slotID) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID)
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
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            //The loaded video file is cached to the local callback
            @Override
            public void onRewardVideoCached() {
                Toast.makeText(MainActivity.this, "rewardVideoAd video cached", Toast.LENGTH_SHORT).show();
            }

            //Video creatives are loaded into, such as title, video url, etc., excluding video files
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Toast.makeText(MainActivity.this, "rewardVideoAd loaded", Toast.LENGTH_SHORT).show();
                mttRewardVideoAd = ad;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        Toast.makeText(MainActivity.this, "rewardVideoAd show", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Toast.makeText(MainActivity.this, "rewardVideoAd bar click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClose() {
                        Toast.makeText(MainActivity.this, "rewardVideoAd close", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoComplete() {
                        Toast.makeText(MainActivity.this, "rewardVideoAd complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoError() {
                        Toast.makeText(MainActivity.this, "rewardVideoAd onVideoError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Toast.makeText(MainActivity.this, "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSkippedVideo() {

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


}
