package com.kkwinter.applink.dp;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import com.kkwinter.applink.Tools;

import java.util.concurrent.atomic.AtomicInteger;

public class ScreenReceiver {


    private static final String TAG = "ScreenReceiver";


    public static void register(Context context) {


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);

        context.registerReceiver(screenBoradcast, intentFilter);

    }

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static BroadcastReceiver screenBoradcast = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {


            String action = intent.getAction();

            Log.i(TAG, "onReceive: >>>>>>" + action);


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    Tools.openHome(context.getApplicationContext());

                }
            }, 5000);


//
//            //黑屏状态，false；  解锁状态，true， 开屏使用状态，true
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
//            Log.i(TAG, "onReceive:  PowerManager：" + isScreenOn);
//
//
//            //锁屏、解锁界面，true，开屏使用状态，false
//            KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//            boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
//            Log.i(TAG, "onReceive:  KeyguardManager：" + flag);

        }
    };


}
