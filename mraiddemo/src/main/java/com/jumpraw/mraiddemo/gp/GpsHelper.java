package com.jumpraw.mraiddemo.gp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import com.jumpraw.mraiddemo.Utils.ContextHolder;
import com.jumpraw.mraiddemo.Utils.ThreadPoolProxy;

import androidx.annotation.Keep;


public class GpsHelper {

    private static final String ADVERTISING_ID_KEY = "advertisingId";
    private static final String IS_LIMIT_AD_TRACKING_ENABLED_KEY = "isLimitAdTrackingEnabled";
    private static final String sAdvertisingIdClientClassName =
            "com.google.android.gms.ads.identifier.AdvertisingIdClient";
    private static String advertisingId = null;

    //初始化的时候调用
    public static void startLoadGaid() {
        asyncFetchAdvertisingInfoIfNotCached(ContextHolder.getGlobalAppContext(), null);
    }


    @Keep
    public static String getAdvertisingId() {
//        return TextUtils.isEmpty(advertisingId) ? PreferenceTools.getString(ADVERTISING_ID_KEY) : advertisingId;
        return advertisingId;
    }

    public static boolean isLimitAdTrackingEnabled() {
//        long value = PreferenceTools.getLong(IS_LIMIT_AD_TRACKING_ENABLED_KEY, 0);
//        return 1 == value;
        return true;
    }


    private static void asyncFetchAdvertisingInfoIfNotCached(Context context, GpsHelperListener gpsHelperListener) {

        if (TextUtils.isEmpty(advertisingId)) {
            asyncFetchAdvertisingInfo(context, gpsHelperListener);
        } else {
            if (gpsHelperListener != null) {
                gpsHelperListener.onFetchAdInfoCompleted();
            }
        }
    }

    private static void asyncFetchAdvertisingInfo(final Context context, final GpsHelperListener gpsHelperListener) {


        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Reflection.MethodBuilder methodBuilder =
                            MethodBuilderFactory.create(null, "getAdvertisingIdInfo")
                                    .setStatic(Class.forName(sAdvertisingIdClientClassName))
                                    .addParam(Context.class, context);

                    Object adInfo = methodBuilder.execute();

                    if (adInfo != null) {
                        GpsHelper.updateSharedPreferences(context, adInfo);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (gpsHelperListener != null) {
                                    gpsHelperListener.onFetchAdInfoCompleted();
                                }
                            }
                        });
                        return;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                try {
                    AdvertisingIdClient.AdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    if (adInfo != null) {
                        GpsHelper.updateSharedPreferences(context, adInfo);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (gpsHelperListener != null) {
                                gpsHelperListener.onFetchAdInfoCompleted();
                            }
                        }
                    });
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        });
    }

    //获取到advertisingId
    public static void updateSharedPreferences(Context context, Object adInfo) {
        advertisingId = reflectedGetAdvertisingId(adInfo, "");
        boolean isLimitAdTrackingEnabled = reflectedIsLimitAdTrackingEnabled(adInfo, false);

//        PreferenceTools.persistString(ADVERTISING_ID_KEY, advertisingId);
//        PreferenceTools.persistLong(IS_LIMIT_AD_TRACKING_ENABLED_KEY, isLimitAdTrackingEnabled ? 1 : 0);
    }

    private static String reflectedGetAdvertisingId(Object adInfo, String defaultValue) {
        try {
            return (String) MethodBuilderFactory.create(adInfo, "getId").execute();
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    static boolean reflectedIsLimitAdTrackingEnabled(Object adInfo, boolean defaultValue) {
        try {
            Boolean result =
                    (Boolean) MethodBuilderFactory.create(adInfo, "isLimitAdTrackingEnabled").execute();
            return (result != null) ? result : defaultValue;
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public interface GpsHelperListener {
        void onFetchAdInfoCompleted();
    }

}
