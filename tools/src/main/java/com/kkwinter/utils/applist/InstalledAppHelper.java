package com.kkwinter.utils.applist;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.kkwinter.utils.WLog;
import com.kkwinter.utils.ThreadPoolProxy;
import com.kkwinter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class InstalledAppHelper {

    private static String INSTALL_APP_LIST_AS_BLOOM_FILTER;
    private static List<String> INSTALL_APP_LIST = new ArrayList<>();
    private static long lastUpdateTime = 0;

    public static String getInstalledAppsAsBloomFilter() {
        return INSTALL_APP_LIST_AS_BLOOM_FILTER;
    }

    private static boolean isInstalledAppListReady() {
        long CACHE_DURATION_10_MINUTES = 10 * 60 * 1000;
        return (!TextUtils.isEmpty(INSTALL_APP_LIST_AS_BLOOM_FILTER))
                && lastUpdateTime > (System.currentTimeMillis() - CACHE_DURATION_10_MINUTES);
    }

    public static void loadApplist(Context context, AppListListener appListListener) {

        if (isInstalledAppListReady()) {
            if (appListListener != null) {
                appListListener.onFetchAppInfoCompleted();
            }
        } else {
            loadApplistInBackground(context, appListListener);
        }
    }

    private static final Object backgroundThreadLock = new Object();

    private static void loadApplistInBackground(final Context context, final AppListListener appListListener) {
        synchronized (backgroundThreadLock) {
            WLog.i("InstalledAppHelper >> fetch installed app list");

            ThreadPoolProxy.getInstance().execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        INSTALL_APP_LIST = Utils.getInstalledApps(context);
                        INSTALL_APP_LIST_AS_BLOOM_FILTER = WBloomFilter.convert(INSTALL_APP_LIST);
                        lastUpdateTime = System.currentTimeMillis();
                    } catch (Exception e) {
                        WLog.printStackTrace(e);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (appListListener != null) {
                                appListListener.onFetchAppInfoCompleted();
                            }
                        }
                    });
                }
            });
        }
    }

    public static void updateInstalledAppsList(String packageName) {
        synchronized (backgroundThreadLock) {
            if (INSTALL_APP_LIST == null || INSTALL_APP_LIST.size() == 0) {
                return;
            }

            if (!INSTALL_APP_LIST.contains(packageName)) {
                INSTALL_APP_LIST.add(packageName);
                INSTALL_APP_LIST_AS_BLOOM_FILTER = WBloomFilter.convert(INSTALL_APP_LIST);
            }
        }

    }

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public interface AppListListener {
        void onFetchAppInfoCompleted();
    }

}
