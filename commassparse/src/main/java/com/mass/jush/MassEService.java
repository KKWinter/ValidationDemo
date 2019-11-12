package com.mass.jush;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.mass.core.GuidianContentManager;

public class MassEService extends Service {
    private GuidianContentManager cm;
    private static final String TAG = "MassEService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: >>>>");
        cm = new GuidianContentManager(this, MassService.class);
        cm.loadContent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: >>>");
        cm.releaseContent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

}
