package com.phone.sample;

import android.app.Application;
import android.content.Intent;

import com.facebook.stetho.Stetho;

import java.util.Map;

/**
 * Created by huangdong on 2018/11/13.
 * antony.huang@yeahmobi.com
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);


//        HookUtil hookUtil=new HookUtil(TestActivity.class, this);
//        hookUtil.hookAms();


//        // init sdk
//        MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
//        // test appId and appKey
//        String appId = "92762";
//        String appKey = "936dcbdd57fe235fd7cf61c2e93da3c4";
//        Map<String, String> map = sdk.getMTGConfigurationMap(appId, appKey);
//        // if you modify applicationId, please add the following attributes,
//        // otherwise it will crash
//        // map.put(MIntegralConstans.PACKAGE_NAME_MANIFEST, "your AndroidManifest
//        // package value");
//        sdk.init(map, this);
//
//
//        ZcoupSDK.initialize(this, "1601");

    }
}
