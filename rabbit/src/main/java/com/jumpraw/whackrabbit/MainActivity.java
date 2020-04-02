package com.jumpraw.whackrabbit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;

import java.lang.annotation.Native;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TTAdNative mTTAdNative;
    private TTFullScreenVideoAd mttFullVideoAd;
    private TTRewardVideoAd mttRewardVideoAd;
    private Context context;
    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        TTAdManager ttAdManager = TTAdSdk.getAdManager();
        ttAdManager.requestPermissionIfNecessary(context);
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());


        container = findViewById(R.id.container);
        findViewById(R.id.bt0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                loadBannerAd("945111979");

                loadNativeAd("945106503");

//                loadExpressNativeAd("945111979");
            }

        });



        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFullScreenVideoAd("945105375");  //whackrabbit

            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                hookPackageName(context, "com.jumpraw.whackrabbit");

                loadRewardVideoAd("945105337");

            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String sha1 = AppSigning.getSha1(getApplicationContext());
//                Log.i(TAG, "SHA1: >>>>" + sha1);
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

    private void loadBannerAd(String slotID) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID)

                .setAdCount(1)
                .setSupportDeepLink(true)
                .build();

        mTTAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError: >>" + i + ", msg: " + s);
            }

            @Override
            public void onBannerAdLoad(TTBannerAd ttBannerAd) {
                View bannerView = ttBannerAd.getBannerView();
                ttBannerAd.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int i) {
                        Log.i(TAG, "onAdClicked: >>>>>>");
                    }

                    @Override
                    public void onAdShow(View view, int i) {
                        Log.i(TAG, "onAdShow: ============");
                    }
                });

                ttBannerAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {

                    }

                    @Override
                    public void onDownloadActive(long l, long l1, String s, String s1) {

                    }

                    @Override
                    public void onDownloadPaused(long l, long l1, String s, String s1) {

                    }

                    @Override
                    public void onDownloadFailed(long l, long l1, String s, String s1) {

                    }

                    @Override
                    public void onDownloadFinished(long l, String s, String s1) {

                    }

                    @Override
                    public void onInstalled(String s, String s1) {

                    }
                });

                if (container != null) {
                    container.removeAllViews();
                    container.addView(bannerView);
                }
            }
        });

    }


    private void loadExpressNativeAd(String slotID) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(300, 100) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(640, 320) //这个参数设置即可，不影响个性化模板广告的size
                .build();

        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                container.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
//                bindAdListener(mTTAd);



                mTTAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {

                        Log.i(TAG, "onAdClicked: ");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.i(TAG, "onAdShow: ");
                    }

                    @Override
                    public void onRenderFail(View view, String msg, int code) {
                        Log.i(TAG, "onRenderFail: ");
                    }

                    @Override
                    public void onRenderSuccess(View view, float width, float height) {
                        //返回view的宽高 单位 dp

                        if (container != null) {
                            container.removeAllViews();
                            container.addView(view);
                        }

                    }
                });


                mTTAd.render();//调用render开始渲染广告
            }
        });


    }


    private void loadNativeAd(String slotID) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 257)
                .setNativeAdType(AdSlot.TYPE_BANNER)
                .setAdCount(1)
                .build();

        mTTAdNative.loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError: >>" + i + ", msg: " + s);
            }

            @Override
            public void onNativeAdLoad(List<TTNativeAd> list) {
                if (list.get(0) == null) {
                    return;
                }

                TTNativeAd ad = list.get(0);


                TextView textView = getTextView(context, ad.getTitle());
                ad.registerViewForInteraction(container, textView, new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                        Log.i(TAG, "onAdClicked: >>>>>>>>>");
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
                        Log.i(TAG, "onAdCreativeClick: ============");
                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {
                        Log.i(TAG, "onAdShow: ================");
                    }
                });


                if (container != null) {
                    container.removeAllViews();
                    container.addView(textView);
                }

            }
        });

    }

    private TextView getTextView(Context context, String content) {

        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setText(content);
        textView.setTextSize(30);
        textView.setTextColor(Color.parseColor("#000000"));

        return textView;
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


    private void loadRewardVideoAd(String slotID) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(slotID)
                .setSupportDeepLink(true)
                .setAdCount(2)
                .setImageAcceptedSize(1080, 1920)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(3)   //奖励的数量
                //必传参数，表来标识应用侧唯一用户；若非服务器回调模式或不需sdk透传
                //可设置为空字符串
                .setUserID("")
                .setOrientation(TTAdConstant.VERTICAL)  //设置期望视频播放的方向，为TTAdConstant.HORIZONTAL或TTAdConstant.VERTICAL
                .build();


        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {


            @Override
            public void onError(int code, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            //视频广告加载后的视频文件资源缓存到本地的回调
            @Override
            public void onRewardVideoCached() {
                Toast.makeText(context, "rewardVideoAd video cached", Toast.LENGTH_SHORT).show();
            }

            //视频广告素材加载到，如title,视频url等，不包括视频文件
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                mttRewardVideoAd = ad;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Toast.makeText(context, "rewardVideoAd show", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Toast.makeText(context, "rewardVideoAd bar click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClose() {
                        Toast.makeText(context, "rewardVideoAd close", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoComplete() {
                        Toast.makeText(context, "rewardVideoAd complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoError() {
                        Toast.makeText(context, "onVideoError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Toast.makeText(context, "verify:" + rewardVerify + " amount:" + rewardAmount +
                                        " name:" + rewardName,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSkippedVideo() {

                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {

                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {

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
