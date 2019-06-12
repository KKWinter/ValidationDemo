package com.jumpraw.windowmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


//        checkPermission();

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    //申请悬浮窗权限
    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                startActivity(intent);
            }
        }
    }


    //展示一个悬浮窗
    private void showWM() {

        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.GRAY);
        textView.setText("我是悬浮窗");
        textView.setTextSize(20);
        textView.setTextColor(Color.RED);

        //类型是TYPE_TOAST，像一个普通的Android Toast一样。这样就不需要申请悬浮窗权限了。

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
//            params.type = WindowManager.LayoutParams.TYPE_TOAST;
//        }

        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = 500;
        params.height = 300;
        params.gravity = Gravity.BOTTOM;
        params.format = PixelFormat.RGBA_8888;

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplication(), "不需要权限的悬浮窗实现", Toast.LENGTH_LONG).show();
//            }
//        });


        windowManager.addView(textView, params);
//        windowManager.updateViewLayout();
//        windowManager.removeView();


//type


// flags
//        FLAG_NOT_FOCUSABLE：表示Window不需要获取焦点，也不需要接收各种输入事件，此标记会同时启用FLAG_NOT_TOUCH_MODAL，最终事件会直接传递给下层具有焦点的Window。
//        FLAG_NOT_TOUCH_MODAL：在此模式下，系统会将当前Window区域以外的点击事件传递给底层的Window，当前Window区域以内的点击事件则自己处理。
//        FLAG_SHOW_WHEN_LOCKED：开启此模式可以让window显示在锁屏界面。

    }


}
