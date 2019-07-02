package com.mass.jush;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.mass.core.GuidianContentManager;

public class MassEService extends Service {
    private GuidianContentManager cm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cm = new GuidianContentManager(this, MassService.class);
        cm.loadContent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cm.releaseContent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

}
