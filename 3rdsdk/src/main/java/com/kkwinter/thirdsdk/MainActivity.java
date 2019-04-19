package com.kkwinter.thirdsdk;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.kkwinter.thirdsdk.advsdk.b.l;
import com.kkwinter.thirdsdk.advsdk.entry.ADVEntry;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "advsdk";
    private Context context;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    String downloadMainUrl = "http://adv-upyun.test.upcdn.net/android/adv/qsz/advsdk/release/advsdk-release.enc";
    String downloadMirrorUrl = "http://adv-upyun.test.upcdn.net/android/adv/qsz/advsdk/release/advsdk-release.enc";
    String md5 = "ea93ac4404f8525f7d90e2410fd1b5c3";
    String path = Environment.getExternalStorageDirectory() + "/advDB/ly_0325_s_cacheADV.dex";
    StringBuilder sb = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        checkPermission();

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //advsdk
                ADVEntry.entry(context);

            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                l ll = l.a(context);
                String result = ll.toString();

                Log.i(TAG, "onClick: >>>ll>>>" + result);

            }
        });

        findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = com.kkwinter.thirdsdk.advsdk.b.g.a("orHbAaAQ30z/qN14H38=");

                Log.i(TAG, "onClick: >>>>" + result);

            }
        });





        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    public final void run() {

                        boolean bo = com.kkwinter.thirdsdk.advsdk.b.c.a(downloadMainUrl, downloadMirrorUrl, md5, path, sb);

                        Log.i(TAG, "useAppContext: >>>>" + bo);


                    }
                }).start();

            }
        });

    }






    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permissions, 111);
                }
            }
        }
    }

}
