package com.kkwinter.utils.debug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.kkwinter.utils.config.Const;

/**
 * adb shell am broadcast -a action --ez LOG true    开启日志
 * adb shell am broadcast -a action --ez TEST true   开启测试模式
 */
public class DebugSwitch {

    private static final String SWITCH_LOG = "LOG";
    private static final String SWITCH_TEST = "TEST";
    private static boolean hasRegister = false;

    public static void registerReceiver(Context context, String action) {
        if (hasRegister) {
            return;
        }
        hasRegister = true;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        context.registerReceiver(debugBroadcastReceiver, intentFilter);
    }

    private static BroadcastReceiver debugBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.hasExtra(SWITCH_LOG)) {
                Const.LOG = intent.getBooleanExtra(SWITCH_LOG, false);

            } else if (intent.hasExtra(SWITCH_TEST)) {
                Const.ISDEBUG = intent.getBooleanExtra(SWITCH_TEST, false);

            }
        }
    };

}
