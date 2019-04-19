package com.kkwinter.applink.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.Context.ALARM_SERVICE;

public class TimingTaskManager {

    private static final String TAG = "ApplinkTimer";

    public final static String PHRASE_ALARM = "alarm.jumpraw.phrase.trigger";
    public final static String DEEPLINK_ACTION = "alarm.jumpraw.deeplink.trigger";

    private static AlarmReceiver receiver = null;


    public static void registerBroadcast(Context context) {

        if (receiver == null) {
            receiver = new AlarmReceiver();

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PHRASE_ALARM);
            intentFilter.addAction(DEEPLINK_ACTION);
            context.registerReceiver(receiver, intentFilter);
        }

    }




    //重复开启多个
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void setPhraseAlarm(Context context) {

        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        int tag = atomicInteger.getAndIncrement();

        for (int i = 0; i < 3; i++) {

            Intent intent = new Intent(PHRASE_ALARM);
            intent.putExtra("index", i);
            intent.putExtra("tag", tag);
            PendingIntent pi = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            long currentTime = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTime);
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 05);
            calendar.set(Calendar.SECOND, 00);

            //定时时间
            long selectTime = calendar.getTimeInMillis();

            //如果当前时间大于设置的时间，那么就从第二天的设定时间开始
            if (currentTime > selectTime) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                selectTime = calendar.getTimeInMillis();
            }

            Log.i(TAG, "setPhraseAlarm: >>>>" + getDateString(selectTime));

            if (Build.VERSION.SDK_INT >= 19) {
                manager.setExact(AlarmManager.RTC_WAKEUP, selectTime, pi);
            } else {
                manager.set(AlarmManager.RTC_WAKEUP, selectTime, pi);
            }

        }

    }


    public static void setDeepLinkAlarm(Context context) {

        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);


        Intent intent = new Intent(DEEPLINK_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);


        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 0);


        //定时时间
        long selectTime = calendar.getTimeInMillis();

        //如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if (currentTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }

        Log.i(TAG, "setDeeplink: >>>>" + getDateString(selectTime));

        if (Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.RTC_WAKEUP, selectTime, pi);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, selectTime, pi);
        }

    }


    private static String getDateString(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }


//    AlarmManager.RTC，硬件闹钟，不唤醒手机（也可能是其它设备）休眠；当手机休眠时不发射闹钟。
//    AlarmManager.RTC_WAKEUP，硬件闹钟，当闹钟发躰时唤醒手机休眠；
//    AlarmManager.ELAPSED_REALTIME，真实时间流逝闹钟，不唤醒手机休眠；当手机休眠时不发射闹钟。
//    AlarmManager.ELAPSED_REALTIME_WAKEUP，真实时间流逝闹钟，当闹钟发躰时唤醒手机休眠；
//    RTC闹钟和ELAPSED_REALTIME最大的差别就是前者可以通过修改手机时间触发闹钟事件，后者要通过真实时间的流逝，即使在休眠状态，时间也会被计算。


/*
1.ELAPSED_REALTIME：以手机开机的时间为基准
2.ELAPSED_REALTIME_WAKEUP：以手机开机的时间为基准，并且可以在休眠时发出广播
3.RTC：以UTC标准时间为基准
4.RTC_WAKEUP：以UTC标准时间为基准，并且可以在休眠时发出广播。这种方式是最常见的形式。
5.POWER_OFF_WAKEUP：关机状态下也能进行提示*/

}
