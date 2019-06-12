package com.kkwinter.thirdsdk;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;


public class App extends Application {


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        Stetho.initializeWithDefaults(context);
    }
}
