package com.kkwinter.applink;

import android.app.Application;

import com.jumpraw.hent.JRhent;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
//        JRhent.initialize(this.getApplicationContext(), "10000");
    }
}
