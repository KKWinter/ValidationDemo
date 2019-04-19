package com.kkwinter.applink.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.kkwinter.applink.Tools;

public class TimingTaskWrapper {

    public final static String PHRASE_ACTION = "com.jumpraw.phrase.alarm";
    public final static String DEEPLINK_ACTION = "com.jumpraw.deeplink.alarm";

    private static TimingTaskWrapper timingTaskWrapper;

    private TimingTaskWrapper() {
    }

    public static TimingTaskWrapper getInstance() {
        if (timingTaskWrapper == null) {
            synchronized (TimingTaskWrapper.class) {
                if (timingTaskWrapper == null) {
                    timingTaskWrapper = new TimingTaskWrapper();
                }
            }
        }
        return timingTaskWrapper;
    }

    private static DeepLinkReceiver deepLinkReceiver;


    public void registerReceiver(Context context, boolean isPhrase, boolean isDeeplink) {
        if (deepLinkReceiver != null || (!isPhrase && !isDeeplink)) {
            return;
        }

        deepLinkReceiver = new DeepLinkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        if (isPhrase) {
            intentFilter.addAction(PHRASE_ACTION);
        }
        if (isDeeplink) {
            intentFilter.addAction(DEEPLINK_ACTION);
        }
        context.registerReceiver(deepLinkReceiver, intentFilter);
    }


    public void unRegisterReceiver(Context context) {
        if (deepLinkReceiver != null) {
            context.unregisterReceiver(deepLinkReceiver);
            deepLinkReceiver = null;
        }
    }


    public class DeepLinkReceiver extends BroadcastReceiver {

        private static final String TAG = "ApplinkTimer";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "onReceive: >>>>" + action);

            if (PHRASE_ACTION.equals(action)) {

                String pcode = intent.getExtras().getString("pcode");
                Log.i(TAG, "onReceive: >>>" + pcode);

                Tools.setClipboard(context, pcode);
            }


        }
    }


}
