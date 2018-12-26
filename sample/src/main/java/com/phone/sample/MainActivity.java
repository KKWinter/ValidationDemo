package com.phone.sample;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by huangdong on 18/10/9.
 * antony.huang@yeahmobi.com
 */
public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    private Context context;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        check();

        Button button = (Button) findViewById(R.id.bt);
        Button button2 = (Button) findViewById(R.id.bt2);
        Button button3 = findViewById(R.id.bt3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                CTContentRes.getInstance().init(context);

                String str = a.b("asx6f3H6foh4FsJ4fsLzYscKrM==");
                Log.i(TAG, "onClick: >>>>" + str);

            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                DownloadTool.load(context);


                context.startActivity(new Intent(context, TestActivity.class));


            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//                context.registerReceiver(new DownloadReceiver(), intentFilter);
            }
        });

    }


    public void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 111);
                }
            }
        }
    }


    public static String getNetworkCountryIso(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkCountryIso();
    }

    public void register() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        localIntentFilter.addDataScheme("package");
//        context.registerReceiver(new MyBroadcastReceiver(), localIntentFilter);
    }


    //判断是否已经静态注册
    public static <T extends BroadcastReceiver> boolean check(Context paramContext, Class<T> paramClass) {
        try {
            PackageManager localPackageManager = paramContext.getPackageManager();
            ActivityInfo localActivityInfo = localPackageManager.getReceiverInfo(new ComponentName(paramContext, paramClass), PackageManager.GET_META_DATA);
            if (null != localActivityInfo) {
                return true;
            }
        } catch (Throwable localThrowable) {
            return false;
        }
        return false;
    }


}
