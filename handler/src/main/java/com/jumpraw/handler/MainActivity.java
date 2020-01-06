package com.jumpraw.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

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


    private Handler handler3 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Log.i(TAG, "handleMessage: >> from handler callback");

            return true;
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {

            Log.i(TAG, "handleMessage: >> from handler3");

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


        findViewById(R.id.cl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                com.jumpraw.handler.sdk.init(context, "72f860777");

            }
        });


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.l.o.sdk.init(context, "72f860777");

            }
        });


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
