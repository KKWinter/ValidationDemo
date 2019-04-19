package com.kkwinter.applink.ph;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.kkwinter.applink.alarm.TimingTaskWrapper;
import com.kkwinter.applink.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class JRPHManager {

    private static final String TAG = "JRPHManager";


    public static void setPhraseAlarm(Context context, List<PhraseVo> phraseVoList) {
        if (context == null || phraseVoList == null || phraseVoList.size() == 0) {
            return;
        }

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (PhraseVo phraseVo : phraseVoList) {
            String pCode = phraseVo.pCode;
            String pTime = phraseVo.pTime;
            int[] ptimes = Tools.getExecTime(pTime);
            if (ptimes == null || ptimes.length < 3) {
                ptimes = new int[]{0, 0, 1};
            }
            int index = phraseVoList.indexOf(phraseVo);


            Intent intent = new Intent(TimingTaskWrapper.PHRASE_ACTION);
            intent.putExtra("pcode", pCode);
            PendingIntent pi = PendingIntent.getBroadcast(context, index, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            long currentTime = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTime);
            calendar.set(Calendar.HOUR_OF_DAY, ptimes[0]);
            calendar.set(Calendar.MINUTE, ptimes[1]);
            calendar.set(Calendar.SECOND, ptimes[2]);

            //定时时间
            long selectTime = calendar.getTimeInMillis();

            //如果当前时间大于设置的时间，那么就从第二天的设定时间开始
            if (currentTime > selectTime) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                selectTime = calendar.getTimeInMillis();
            }

            Log.i(TAG, "setPhraseAlarm: >>>>" + getDateString(selectTime));

            //设置定时任务
            if (Build.VERSION.SDK_INT >= 19) {
                manager.setExact(AlarmManager.RTC_WAKEUP, selectTime, pi);
            } else {
                manager.set(AlarmManager.RTC_WAKEUP, selectTime, pi);
            }
        }
    }


    private static String getDateString(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }


}
