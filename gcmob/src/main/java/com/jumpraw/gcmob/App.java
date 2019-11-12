package com.jumpraw.gcmob;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.pub18828.core.api.SDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this.getApplicationContext());

        SDK.initSDK(getApplicationContext(), "90002", "3fc7cbe75b45e7ce18b7f54b5edfda39");
    }
}
