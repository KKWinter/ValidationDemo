package com.phone.sample;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by huangdong on 2018/11/21.
 * antony.huang@yeahmobi.com
 */
public class DownloadReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        
        String action = intent.getAction();
        Uri uri = intent.getData();
        String packageName = intent.getPackage();

        String finalAction = DownloadManager.ACTION_DOWNLOAD_COMPLETE;

        Log.i(CTContentRes.TAG, "onReceive: >>>>>>>>>>>>>>>" + action);
    }
}
