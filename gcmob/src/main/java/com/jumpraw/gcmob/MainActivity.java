package com.jumpraw.gcmob;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;

import com.pub18828.core.a.e;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this.getApplicationContext();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //android api 17之后，可以用这种方式获取ua
            String ua = WebSettings.getDefaultUserAgent(this.getApplicationContext());
        }


        try {
            Class cls = Class.forName("com.jumpraw.gcmob.a");
            Field[] fileds = cls.getDeclaredFields();
            for (Field filed : fileds) {

                filed.setAccessible(true);
                String name = filed.getName();
                Object result = filed.get(null);

                Log.i("result", "onCreate: " + name + ", value> "+ result);

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        String pkg = "com.jumpraw.demo";

        if (b.a(context, pkg)) {

            Log.i("launch", "onCreate: >>pkg exit: " + pkg);
            b.b(context, pkg);
        }

    }
}
