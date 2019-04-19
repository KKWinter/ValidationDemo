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
import android.webkit.WebSettings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import dalvik.system.DexClassLoader;


/**
 * 反射，动态加载dex包
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "reflect";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String path3 = Environment.getExternalStorageDirectory() + "/promote_1.2.dex";
    private String path4 = Environment.getExternalStorageDirectory() + "/promote_1.3.dex";

    private String proPath = Environment.getExternalStorageDirectory() + "/JRPro_1.0.0.dex";
    private String advPath = Environment.getExternalStorageDirectory() + "/JRPro_1.1.0.dex";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        context = this;

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loadDex(proPath, "10000");


            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDex(advPath, "10001");

            }
        });
    }


    /**
     * 判断assets文件夹下的文件是否存在
     *
     * @return false 不存在    true 存在
     */
    public static void isFileExists(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            String[] names = assetManager.list("");
            Log.i(TAG, "isFileExists: >>>>>" + names.toString());
            for (String name : names) {
                Log.i(TAG, "isFileExists: <<<<<<" + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadDex(String path, String slotID) {

        try {
            File file = new File(path);
            Log.i(TAG, "loadDex: " + path);
            if (file.exists()) {
                Log.i(TAG, "loadDex: ========" + file.getAbsolutePath());
            }

            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), context.getDir("case", 0).getAbsolutePath(), null, context.getClassLoader());
            Class cl = dexClassLoader.loadClass("com.jumpraw.pro.JRPro");
            Method method = cl.getDeclaredMethod("initialize", Context.class, String.class);
            method.setAccessible(true);
            method.invoke(cl.newInstance(), context, slotID);

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

            File writeFile = new File(path3);
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
