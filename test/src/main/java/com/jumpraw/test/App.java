package com.jumpraw.test;

import android.app.Application;

import com.facebook.stetho.Stetho;

import test.android.com.new_lib.WorkInit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        WorkInit.getInstance().init(this);
    }
}
