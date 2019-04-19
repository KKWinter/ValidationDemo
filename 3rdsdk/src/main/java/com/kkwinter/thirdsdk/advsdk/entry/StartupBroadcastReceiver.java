package com.kkwinter.thirdsdk.advsdk.entry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class StartupBroadcastReceiver extends BroadcastReceiver {
    public StartupBroadcastReceiver() {
    }

    public void onReceive(Context var1, Intent var2) {
        try {
            WorkService.startServer(var1, var2.getAction());
        } catch (Exception var3) {
            ;
        }
    }
}
