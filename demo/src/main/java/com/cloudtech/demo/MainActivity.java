package com.cloudtech.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final String TAG = "dexloader";
    private String writePath = Environment.getExternalStorageDirectory() + "/shell.dex";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        checkPermission();


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readFileFromAssets();

            }
        });


        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadShellDex(writePath);

            }
        });

    }


    //判断广告是否静态注册
    public static <T extends BroadcastReceiver> boolean isReceiverOn(Context paramContext, Class<T> paramClass) {
        try {
            PackageManager localPackageManager = paramContext.getPackageManager();
            ActivityInfo localActivityInfo = localPackageManager.getReceiverInfo(new ComponentName(paramContext, paramClass), PackageManager.MATCH_DEFAULT_ONLY);
            if (null != localActivityInfo) {
                return true;
            }
        } catch (Throwable localThrowable) {
            return false;
        }
        return false;
    }


    public void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 321);
                }
            }
        }
    }


    private void readFileFromAssets() {

        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("julegou.dex");

            File writeFile = new File(writePath);
            if (!writeFile.exists()) {
                writeFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(writeFile);

            int length;
            byte[] bytes = new byte[1024];
            while ((length = is.read(bytes, 0, 1024)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
            }

            fos.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void loadDex(String path, String slotId) {

        try {
            File file = new File(path);
            Log.i(TAG, "loadDex: " + path);
            if (!file.exists()) {
                Log.i(TAG, "loadDex: ========");
                return;
            }

            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), context.getDir("case", 0).getAbsolutePath(), null, context.getClassLoader());
            Class cl = dexClassLoader.loadClass("luna.migo.physics.balls.core.CTService");

            Method method = cl.getDeclaredMethod("init", Context.class, String.class);
            method.setAccessible(true);
            method.invoke(cl.newInstance(), context, slotId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadShellDex(String path) {

        try {
            File file = new File(path);
            Log.i(TAG, "loadDex: " + path);

            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), context.getDir("case", 0).getAbsolutePath(), null, context.getClassLoader());
            Class cl = dexClassLoader.loadClass("com.cloudtech.sdkshell.SDKService");

            Method method = cl.getDeclaredMethod("initialize", Context.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(cl.newInstance(), context, "206", "600");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
