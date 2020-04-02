package com.jumpraw.compass;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.fyber.FairBid;

public class App extends Application {


    private static App sApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        Stetho.initializeWithDefaults(sApplication);

    }

    public static App getApp() {
        return sApplication;
    }



}
