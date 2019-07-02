package com.mass.jush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zhj_7 on 2016/11/18.
 */

public class MassEntry extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.e("Entry", "receive: " + intent);
    	MassNLService.autoAuthorise(context);
        MassAssistant.check(context, intent.getAction());
    }
}
