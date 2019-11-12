package com.jumpraw.webview;

import android.app.Application;
import android.content.Context;


public class App extends Application {


    public static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();


    }


}
