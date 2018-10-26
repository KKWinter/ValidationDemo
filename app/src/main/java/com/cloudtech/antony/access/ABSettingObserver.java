package com.cloudtech.antony.access;


import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.cloudtech.antony.AntonyApplication;
import com.cloudtech.antony.CTLog;
import com.cloudtech.antony.MainActivity;

/**
 * Created by huangdong on 18/8/28.
 * antony.huang@yeahmobi.com
 */
public class ABSettingObserver {

    private static ABSettingObserver settingsObserver;

    private ABSettingObserver() {

    }

    public static ABSettingObserver getInstance() {
        if (settingsObserver == null) {
            synchronized (ABSettingObserver.class) {
                if (settingsObserver == null) {
                    settingsObserver = new ABSettingObserver();
                }
            }
        }
        return settingsObserver;
    }


    private SecureContentObserver secureContentObserver;

    public void registerObserver(Context context) {
        if (secureContentObserver != null) {
            return;
        }

        try {
            CTLog.d(">>>registerObserver");
            secureContentObserver = new SecureContentObserver(new Handler(Looper.getMainLooper()));
//            context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.ACCESSIBILITY_ENABLED), false, secureContentObserver);
            context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES), false, secureContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void unregisterObserver(Context context) {
        if (secureContentObserver != null) {
            CTLog.d(">>>unregisterObserver");
            context.getContentResolver().unregisterContentObserver(secureContentObserver);
            secureContentObserver = null;
        }
    }


    class SecureContentObserver extends ContentObserver {

        public SecureContentObserver(Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange, Uri uri) {
            CTLog.d("onChange >>>>>>>" + selfChange + ">>>>>" + uri.toString());

            Context context = AntonyApplication.context;
            boolean isEnabled = ABSettingHelper.getInstance().checkAccessibilityService(context, MainActivity.SERVER_NAME);
            //拔掉USB，强制一直开启； 插上USB，不干涉用户行为

            if (!MainActivity.isUSBConnected && !isEnabled) {
                ABSettingHelper.getInstance().updateAccessibilityService(context, MainActivity.SERVER_NAME, true);
            }

        }
    }

}
