package com.cloudtech.antony;

import android.app.Application;
import android.content.Context;

/**
 * Created by huangdong on 18/8/30.
 * antony.huang@yeahmobi.com
 */
public class AntonyApplication extends Application {


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }
}
