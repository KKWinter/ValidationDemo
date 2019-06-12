package com.phone.ndkdemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by huangdong on 2018/11/1.
 * antony.huang@yeahmobi.com
 */
public class MainActivity extends Activity {

    private static final String TAG = "ndk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.ndk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                JNITest.get();

//                String type = Build.TYPE;
//                Log.i(TAG, "onClick: >>type>: " + type);

                get();

            }
        });
    }


    private void get(){

        String type = null;
        String id = null;
        try {
            Method method = Build.class.getDeclaredMethod("getString", String.class);
            method.setAccessible(true);
            type = (String) method.invoke(new Build(), "ro.build.type");
            id = (String) method.invoke(new Build(), "ro.build.display.id");

            Log.i(TAG, "get: >>>" + type);
            Log.i(TAG, "get: ==" + id);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



    }
}
