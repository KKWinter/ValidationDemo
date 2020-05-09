package com.box.app.news;

import android.app.Application;

import com.jumpraw.mediation.GCAdConfig;
import com.jumpraw.mediation.GCMedSDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        GCMedSDK.init(this, new GCAdConfig.Builder()
                .setSlotId("10143")
                .setAppName("集成应⽤用的名称")
                .build());
    }
}
