package com.kkwinter.applink.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "ApplinkTimer";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.i(TAG, "onReceive: >>>>" + action);

//        if (TimingTaskManager.PHRASE_ALARM.equals(action)) {
//
//            int index = intent.getExtras().getInt("index");
//            int tag = intent.getExtras().getInt("tag");
//            Log.i(TAG, "onReceive: >>index>> " + index + ">>>tag>>>" + tag);
//        }
    }
}
