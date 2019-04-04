package com.cloudtech.antony;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cloudtech.antony.utils.CTLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "deeplink";
    private Context context;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static boolean isUSBConnected = false;
    public static final String SERVER_NAME = "com.cloudtech.antony/com.cloudtech.antony.access.ABService";

    public static String mainAmazon = "https://www.amazon.in";
    public static String amazon = "https://www.amazon.in/gp/product/B077PWBC7J/ref=as_li_tl?ie=UTF8&tag=cloudmobi20-21&camp=3638&creative=24630&linkCode=as2&creativeASIN=B077PWBC7J&linkId=db4ce0d4471b8a342fc93c4b7b221ebf";

    public static String aiqiyi = "iqiyi://mobile/home?ftype=27&subtype=lk2_527";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
//        checkPermission();

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open();

            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }



    //跳转系统自带界面 辅助功能界面
    private void open() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "打开辅助功能", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * DeepLink方式打开
     */
    private void openDeepLink(Context context, String deeplink) {
        Log.i(TAG, "openDeepLink: >>>>>>>>" + deeplink);
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = it.resolveActivity(context.getPackageManager());
        if (componentName != null) {    //已经安装该应用
            context.startActivity(it);
        }
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


    private String inputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int length;
        while ((length = inputStream.read(bytes)) != -1) {
            baos.write(bytes, 0, length);
        }
        baos.flush();
        return baos.toString();
    }


    String keyString = "ASIN:B07FJY24WL";
    String partern = "(ASIN|ISBN):\\s*([^\\s]+)";

    private void patten() {

        Pattern p = Pattern.compile(partern);
        Matcher m = p.matcher(keyString);
        boolean bo = m.find();
        CTLog.d(">>>" + bo);
        if (bo) {
            CTLog.d("===" + m.group());
        }

    }

}
