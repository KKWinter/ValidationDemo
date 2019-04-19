package com.kkwinter.applink.dp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class USBReceiver {


    private static final String TAG = "USBReceiver";

    private static USBReceiver usbReceiver;

    private USBReceiver() {

    }

    public static USBReceiver getInstance() {
        if (usbReceiver == null) {
            synchronized (USBReceiver.class) {
                if (usbReceiver == null) {
                    usbReceiver = new USBReceiver();
                }
            }
        }
        return usbReceiver;
    }

    private USBStatusReceiver usbStatusReceiver;

    //注册广播监听
    public void registerReceiver(Context context) {
        if (usbStatusReceiver != null) {
            return;
        }

        try {
            usbStatusReceiver = new USBStatusReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_STATE");

            intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);    //电量变化

            intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);    //开始充电

            intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);  //结束充电


            intentFilter.addAction(Intent.ACTION_POWER_USAGE_SUMMARY);

            context.registerReceiver(usbStatusReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注销广播监听
    protected void unregisterReceiver(Context context) {
        if (usbStatusReceiver != null) {
            context.unregisterReceiver(usbStatusReceiver);
            usbStatusReceiver = null;
        }
    }

    class USBStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG, "onReceive: >>>>" + intent.getAction());



            if (intent.getAction().equals("android.hardware.usb.action.USB_STATE")) {
                boolean isConnected = intent.getExtras().getBoolean("connected");

                Log.i(TAG, "onReceive: >>isConnected>" + isConnected);
            }
        }
    }
}