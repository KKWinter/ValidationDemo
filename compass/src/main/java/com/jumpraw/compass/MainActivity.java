package com.jumpraw.compass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.fyber.FairBid;
import com.fyber.fairbid.ads.Banner;
import com.fyber.fairbid.ads.ImpressionData;
import com.fyber.fairbid.ads.Interstitial;
import com.fyber.fairbid.ads.Rewarded;
import com.fyber.fairbid.ads.banner.BannerError;
import com.fyber.fairbid.ads.banner.BannerListener;
import com.fyber.fairbid.ads.banner.BannerOptions;
import com.fyber.fairbid.ads.banner.SupportedCreativeSizes;
import com.fyber.fairbid.ads.interstitial.InterstitialListener;
import com.fyber.fairbid.ads.rewarded.RewardedListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * fyber的测试demo
 *
 * Applovin的三种类型广告都可以请求到
 *
 * IronSource的还需要提供
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context;
    private String appID = "112765";
    private String interID = "217696";
    private String rewardID = "217698";
    private String bannerID = "217697";
    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        container = findViewById(R.id.container);

        findViewById(R.id.bt0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FairBid.start(appID, MainActivity.this);

                FairBid.configureForAppId(appID)
//                        .enableLogs()
                        .disableAutoRequesting()
                        .start(MainActivity.this);

            }
        });

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Interstitial.request(interID);
                Interstitial.setInterstitialListener(new InterstitialListener() {
                    @Override
                    public void onShow(@NonNull String s, @NonNull ImpressionData impressionData) {

                        Log.i(TAG, "onShow: >>>>>>>" + s);
                    }

                    @Override
                    public void onClick(@NonNull String s) {

                        Log.i(TAG, "onClick: ====" + s);
                    }

                    @Override
                    public void onHide(@NonNull String s) {

                        Log.i(TAG, "onHide: ===" + s);
                    }

                    @Override
                    public void onShowFailure(@NonNull String s, @NonNull ImpressionData impressionData) {

                        Log.i(TAG, "onShowFailure: ===" + s);
                    }

                    @Override
                    public void onAvailable(@NonNull String s) {
                        Log.i(TAG, "interstitial onAvailable: " + s);

                        if (Interstitial.isAvailable(s)) {
                            Interstitial.show(s, MainActivity.this);
                        }
                    }

                    @Override
                    public void onUnavailable(@NonNull String s) {

                        Log.i(TAG, "onUnavailable: >>>" + s);
                    }

                    @Override
                    public void onRequestStart(@NonNull String s) {

                        Log.i(TAG, "onRequestStart: >>>" + s);
                    }
                });

            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //默认在屏幕的底部，
                //可以通过placeAtTheTop放到顶部
                //还可以放到自己的容器中，通过placeInContainer()
                BannerOptions bannerOptions = new BannerOptions()
//                        .withNetworkSize(SupportedCreativeSizes.ADMOB_LEADERBOARD)
//                        .withNetworkSize(SupportedCreativeSizes.FACEBOOK_BANNER_HEIGHT_90)
//                        .placeAtTheBottom()
//                        .placeAtTheTop()
                        .placeInContainer(container);
                Banner.show(bannerID, bannerOptions, MainActivity.this);

                Banner.setBannerListener(new BannerListener() {
                    @Override
                    public void onError(String placementId, BannerError error) {
                        // Called when an error arises when showing the banner from placement 'placementId'
                        Log.i(TAG, "onError: >>" + placementId + ", error>> " + error.getErrorMessage());
                    }

                    @Override
                    public void onLoad(String placementId) {
                        // Called when the banner from placement 'placementId' is successfully loaded
                        Log.i(TAG, "onLoad: >>>" + placementId);
                    }

                    @Override
                    public void onShow(String placementId, ImpressionData impressionData) {
                        // Called when the banner from placement 'placementId' is shown
                        Log.i(TAG, "onShow: ====" + placementId);
                    }

                    @Override
                    public void onClick(String placementId) {
                        // Called when the banner from placement 'placementId' is clicked
                        Log.i(TAG, "onClick: >>>>" + placementId);
                    }

                    @Override
                    public void onRequestStart(String placementId) {
                        // Called when the banner from placement 'placementId' is going to be requested
                        Log.i(TAG, "onRequestStart: >>>>>" + placementId);
                    }
                });

            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Rewarded.request(rewardID);

                Rewarded.setRewardedListener(new RewardedListener() {
                    @Override
                    public void onShow(@NonNull String s, @NonNull ImpressionData impressionData) {
                        Log.i(TAG, "onShow: >>>>" + s);
                    }

                    @Override
                    public void onClick(@NonNull String s) {
                        Log.i(TAG, "onClick: >>>" + s);
                    }

                    @Override
                    public void onHide(@NonNull String s) {
                        Log.i(TAG, "onHide: ====" + s);
                    }

                    @Override
                    public void onShowFailure(@NonNull String s, @NonNull ImpressionData impressionData) {
                        Log.i(TAG, "onShowFailure: ====" + s);
                    }

                    @Override
                    public void onAvailable(@NonNull String s) {
                        Log.i(TAG, "reward onAvailable: ====" + s);
                        if (Rewarded.isAvailable(s)) {
                            Rewarded.show(s, MainActivity.this);
                        }
                    }

                    @Override
                    public void onUnavailable(@NonNull String s) {
                        Log.i(TAG, "onUnavailable: =====" + s);
                    }

                    @Override
                    public void onCompletion(@NonNull String s, boolean b) {
                        Log.i(TAG, "onCompletion: >>>>" + s + ", ===" + b);
                    }

                    @Override
                    public void onRequestStart(@NonNull String s) {
                        Log.i(TAG, "onRequestStart: ====" + s);
                    }
                });


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
