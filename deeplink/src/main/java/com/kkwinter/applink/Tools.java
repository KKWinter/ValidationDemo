package com.kkwinter.applink;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

public class Tools {

    /**
     * 往剪切板写入字符串
     *
     * @param context context
     * @param string  淘口令之类
     */
    public static void setClipboard(Context context, String string) {
        Log.i("clipboard", "setClipboard: >>>>>>" + string);

        if (TextUtils.isEmpty(string)) {
            return;
        }

        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", string);
            // 将ClipData内容放到系统剪贴板里。
            if (cm != null) {
                cm.setPrimaryClip(mClipData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据时间字符串，获取时、分、秒的整数
     *
     * @param time "00:01:00"
     * @return int数组
     */
    public static int[] getExecTime(String time) {
        int[] timeInts;

        try {
            String[] timeStrs = time.split(":");
            timeInts = new int[timeStrs.length];

            for (int i = 0; i < timeStrs.length; i++) {
                timeInts[i] = Integer.valueOf(timeStrs[i]);
            }

            return timeInts;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new int[]{0, 0, 1};
        }
    }


    public static void openDeepLink(Context context, String url) {
        Log.i("Deeplink", "openDeeplink: >>>" + url);

//        if (TextUtils.isEmpty(url)) {
//            throw new NullPointerException("DeepLink Url is null");
//        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
//        if (componentName == null) {
//            throw new ActivityNotFoundException("No Activity found to handle Intent");
//        }
        context.startActivity(intent);
    }


    public static void openHome(Context context) {
        Log.i("openHome", "openHome: 》》》》》》》》》》");

        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(home);
    }

    public static void openmiui(Context context) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.miui.home", "com.miui.home.launcher.ScreenView");
        intent.setComponent(cn);
        context.startActivity(intent);
    }


    private void getScreen(Context context) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
    }

    private void getBattery(Context context) {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus;
        batteryStatus = context.registerReceiver(null, ifilter);

        //获取充电状态（是否在充电）
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

        //判断充电方式
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;    //USB充电
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;    //AC直充
    }


    private static final String GP_HOST = "play.google.com";
    private static final String GP_MARKET_SCHEMA = "market";


    /**
     * 判断是否是目标url，googleplay或deeplink地址
     *
     * @param url url
     * @return true/false
     */
    public static boolean isTragetUrl(String url) {
        return !TextUtils.isEmpty(url) && (isGooglePlayUrl(url) || isDeepLinkUrl(url));
    }


    //检查是不是gp地址
    public static boolean isGooglePlayUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            return GP_MARKET_SCHEMA.equalsIgnoreCase(uri.getScheme())
                    || GP_HOST.equalsIgnoreCase(uri.getHost());
        } catch (Exception e) {
            return false;
        }
    }


    //检查是不是deeplink地址
    public static boolean isDeepLinkUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            return !"http".equalsIgnoreCase(uri.getScheme())
                    && !"https".equalsIgnoreCase(uri.getScheme());
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 不以http、https、market开头的url
     */
    public static boolean isWebViewPossibleCrashDeepLink(String url) {
        //According user crash log, 4.04 webview crash sometimes.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        }

        if (TextUtils.isEmpty(url)) {
            return false;
        }

        try {
            String scheme = Uri.parse(url).getScheme();
            return !GP_MARKET_SCHEMA.equalsIgnoreCase(scheme)
                    && !"http".equalsIgnoreCase(scheme)
                    && !"https".equalsIgnoreCase(scheme);
        } catch (Exception e) {
            return false;
        }
    }

}
