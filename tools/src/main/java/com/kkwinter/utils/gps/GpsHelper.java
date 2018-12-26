package com.kkwinter.utils.gps;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.kkwinter.utils.WLog;
import com.kkwinter.utils.config.Const;
import com.kkwinter.utils.ThreadPoolProxy;

public class GpsHelper {

    private static final String ADVERTISING_ID_KEY = "advertisingId";
    private static String advertisingId;

    //取gaid
    public static String getAdvertisingId() {
        if (Const.DEBUG_USE_EMULATOR) {
            return "GAID_EMULATOR";
        }
        return advertisingId;
    }

    //判断gaid是否可用
    public static boolean isAdvertisingIdAvailable() {
        return Const.DEBUG_USE_EMULATOR || !TextUtils.isEmpty(advertisingId);
    }

    //获取gaid
    public static void loadAdvertisingId(Context context, GpsHelperListener gpsHelperListener) {
        if (isAdvertisingIdAvailable()) {
            if (gpsHelperListener != null) {
                gpsHelperListener.onFetchAdInfoCompleted();
            }
        } else {
            asyncFetchAdvertisingInfo(context, gpsHelperListener);
        }
    }

    private static void asyncFetchAdvertisingInfo(final Context context, final GpsHelperListener gpsHelperListener) {
        WLog.i("GpsHelper >> fetch GoogleAdvertisingInfo(GAID)");

        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    AdvertisingIdClient.AdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    if (adInfo != null) {
                        advertisingId = adInfo.getId();
                    }
                } catch (Exception e) {
                    WLog.printStackTrace(e);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (gpsHelperListener != null) {
                            gpsHelperListener.onFetchAdInfoCompleted();
                        }
                    }
                });
            }
        });
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public interface GpsHelperListener {
        void onFetchAdInfoCompleted();
    }

}
