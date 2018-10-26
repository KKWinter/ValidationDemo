package com.phone.reflect;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;


/**
 * 反射，动态加载dex包
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "reflect";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String path = Environment.getExternalStorageDirectory() + "/subscription_1.1.1.dex";
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        context = this;

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadDex();
            }
        });

    }


    private void loadDex() {

        try {
            File file = new File(path);
            Log.i(TAG, "loadDex: " + path);
            if (file.exists()) {
                Log.i(TAG, "loadDex: ========");
            }

            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), context.getDir("case", 0).getAbsolutePath(), null, context.getClassLoader());
            Class cl = dexClassLoader.loadClass("com.cloudtech.sub.core.CTService");
            Method method = cl.getDeclaredMethod("initialize", Context.class, String.class);
            method.setAccessible(true);
            method.invoke(cl.newInstance(), context, "247");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void reflectClass() {

        try {
            Class cls = Class.forName("com.phone.reflect.SpecialFeature");

//            Method method = cls.getDeclaredMethod("printLog");
//            method.setAccessible(true);
//            method.invoke(cls.newInstance());
//
//            Method method1 = cls.getDeclaredMethod("printLog", String.class);
//            method1.setAccessible(true);
//            method1.invoke(cls.newInstance(), "abcdefg");

            Constructor constructor = cls.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance("bbbbbbbb");

            Field field = cls.getDeclaredField("string");
            field.setAccessible(true);
            Log.i(SpecialFeature.TAG, "reflectClass: field>>" + field.getName());
            Log.i(SpecialFeature.TAG, "reflectClass: field>>" + field.get(obj).toString());

            Method method = cls.getDeclaredMethod("printLog");
            method.setAccessible(true);
            method.invoke(cls.newInstance());

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(SpecialFeature.TAG, "error: " + Log.getStackTraceString(e));
        }


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

            File writeFile = new File(path);
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

}
