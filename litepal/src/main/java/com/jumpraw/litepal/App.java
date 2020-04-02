package com.jumpraw.litepal;

import android.app.Application;

import org.litepal.LitePal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);
    }
}
