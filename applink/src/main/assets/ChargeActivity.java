package com.cloudtech.tools.chargereceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.adjust.sdk.Adjust;
import com.cloudtech.tools.R;

/**
 * 插入USB弹出的页面
 */
public class ChargeActivity extends Activity {
    private Activity activity = null;

    private TextView usb_text;
    private ChargeProgressView usb_progress;

    private IntentFilter mIntentFilter;                   //监听电池电量变化的广播
    private IntentFilter filter;                          //监听断开充电连接的广播

    private int initLevel = 0;                            //初始的电量
    private long initTime = 0;                            //初始的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_usb);

        activity = this;

        //控件对象
        ImageView usb_title_back = (ImageView) findViewById(R.id.usb_title_image);
        usb_text = (TextView) findViewById(R.id.usb_text);
        usb_progress = (ChargeProgressView) findViewById(R.id.usb_progress);

        //返回按钮的点击事件
        usb_title_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //第一次插入的时候
        FirstEnter();

        //然后注册监听电池变化的广播
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        //注册拔出充电连接的广播
        filter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
    }

    private void FirstEnter() {
        Intent intent = getIntent();
        int level = intent.getIntExtra("level", 100);
        float surplusTime = intent.getFloatExtra("surplusTime", 0); //剩余充电时间，单位分钟

        initLevel = level;
        initTime = System.currentTimeMillis();

        usb_progress.setProgress(level);
        String txt = activity.getResources().getString(R.string.overtime) + mathabs(surplusTime);
        usb_text.setText(txt);
    }


    @Override
    protected void onResume() {
        registerReceiver(receiver, filter);
        registerReceiver(mIntentReceiver, mIntentFilter);
        super.onResume();
        Adjust.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Adjust.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        unregisterReceiver(mIntentReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            activity.finish();
        }

    };

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {

        private float perMinute = 0;

        @Override
        public void onReceive(Context context, Intent intent) {

            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 0);

            //判断充电方式
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;           //USB充电
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;                 //AC直充

            //根据变化的1%计算
            if (level - initLevel == 1) {
                //变化之后的时间
                long currentTime = System.currentTimeMillis();
                //变化的时间差
                long addTime = currentTime - initTime;          //单位毫秒
                //充电1%所需要的时间
                perMinute = addTime / (1000 * 60);
            }

            if (perMinute != 0) {

                //剩余要充电的比例
                int surplusVolume = scale - level;
                //剩余要充电的时间
                float surplusTime = surplusVolume * perMinute;
                String txt = activity.getResources().getString(R.string.overtime) + mathabs(surplusTime);
                usb_text.setText(txt);
                //变化之后的电量
                usb_progress.setProgress(level);


                //充满电所需要的时间
                float allTime = 100 * perMinute;
                if (usbCharge) {
                    //如果是USB充电，电池的容量为：
                    int volume = (int) (500 * (allTime / 60));

                } else if (acCharge) {
                    //如果是AC充电，电池的容量为：
                    int volume = (int) (1000 * (allTime / 60));
                }
            }

            if (level == 100) {
                usb_text.setText(R.string.over);
            }

        }
    };

    //把时间算成小时和分钟字符串
    private String mathabs(float num) {
        String h = activity.getResources().getString(R.string.hour);
        String m = activity.getResources().getString(R.string.minute);
        if (num < 60) {
            return Math.round(num) + m;
        } else {
            int hour = (int) (num / 60);
            int minute = (int) (num % 60);

            return hour + h + minute + m;
        }
    }



}
