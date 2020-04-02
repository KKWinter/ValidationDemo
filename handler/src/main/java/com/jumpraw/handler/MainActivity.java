package com.jumpraw.handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "handler";

    private Handler handler1 = new Handler(Looper.getMainLooper());

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            Log.i(TAG, "handleMessage:  >> from handler2");

            super.handleMessage(msg);
        }
    };

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        checkPermission();


        handler1.sendMessage(Message.obtain());


        findViewById(R.id.cl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    PackageManager packageManager = context.getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.android.gcgo");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


//                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//                //api 1
//                if (context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//
//                //ap1 23
//                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    Activity#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for Activity#requestPermissions for more details.
//                    return;
//                }
//
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Utils.NetworkForApi29(context);
//                }

//                Toast.makeText(context, "isnetwork >>" + isNetworkEnable(context), Toast.LENGTH_SHORT).show();
            }
        });


//        OSDK调研
//        com.l.o.sdk.init(context, "72f860777");
    }


    //获取NetworkInfo [实时获取，不可缓存]
    private static NetworkInfo getNetworkInfo(Context context) {
        NetworkInfo networkInfo = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (context.checkCallingOrSelfPermission(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkInfo;
    }


    /**
     * 检查网络链接
     */
    public static boolean isNetworkEnable(Context context) {
        try {
            NetworkInfo networkInfo = getNetworkInfo(context);
            return networkInfo != null && networkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, 321);
                }
            }

        }
    }

}
