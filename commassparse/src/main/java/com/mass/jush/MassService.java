package com.mass.jush;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.mass.core.ContentManager;


/**
 * Created by zhj_7 on 2016/11/18.
 */

public class MassService extends Service {
    private ContentManager cm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        cm = new ContentManager(this);
        cm.loadContent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cm.releaseContent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
