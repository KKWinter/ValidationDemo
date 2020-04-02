package com.kkwinter.testa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import dalvik.system.DexClassLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Class cCls = Class.forName("com.ap.x.t.utils.c");
                    Field[] fields = cCls.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);

                        String result = (String) field.get(null);

                        Log.i(TAG, "onClick: >>>" + result);
                    }


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        });
    }





    public void startTestB() {

        Toast.makeText(context, "Jump to B", Toast.LENGTH_SHORT).show();


        Bundle paramBundle = new Bundle();
        paramBundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);
        String dexpath = "/sdcard/TestB.apk";
        String dexoutputpath = "/sdcard/";
        LoadAPK(paramBundle, dexpath, dexoutputpath);


    }


    public void LoadAPK(Bundle paramBundle, String dexpath, String dexoutputpath) {

        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
        DexClassLoader localDexClassLoader = new DexClassLoader(dexpath, dexoutputpath, null, localClassLoader);

        try {
            //小米，android 10，获取对象为空
            PackageInfo plocalObject = getPackageManager().getPackageArchiveInfo(dexpath, PackageManager.GET_ACTIVITIES);

            if (plocalObject != null && (plocalObject.activities != null) && (plocalObject.activities.length > 0)) {

                String activityname = plocalObject.activities[0].name;
                Log.d(TAG, "activityname = " + activityname);

                Class localClass = localDexClassLoader.loadClass(activityname);
                Constructor localConstructor = localClass.getConstructor(new Class[]{});
                Object instance = localConstructor.newInstance(new Object[]{});
                Log.d(TAG, "instance = " + instance);

                Method localMethodSetActivity = localClass.getDeclaredMethod("setActivity", new Class[]{Activity.class});
                localMethodSetActivity.setAccessible(true);
                localMethodSetActivity.invoke(instance, new Object[]{this});

                Method methodonCreate = localClass.getDeclaredMethod("onCreate", new Class[]{Bundle.class});
                methodonCreate.setAccessible(true);
                methodonCreate.invoke(instance, new Object[]{paramBundle});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
