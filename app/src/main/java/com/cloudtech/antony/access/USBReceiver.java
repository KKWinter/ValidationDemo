package com.cloudtech.antony.access;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.cloudtech.antony.utils.CTLog;
import com.cloudtech.antony.MainActivity;
import com.cloudtech.antony.utils.ToastUtils;

public class USBReceiver {

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
        CTLog.d("USBReceiver registerReceiver");

        try {
            usbStatusReceiver = new USBStatusReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_STATE");
            context.registerReceiver(usbStatusReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注销广播监听
    public void unregisterReceiver(Context context) {
        if (usbStatusReceiver != null) {
            CTLog.d("USBReceiver unregisterReceiver");
            context.unregisterReceiver(usbStatusReceiver);
            usbStatusReceiver = null;
        }
    }

    class USBStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.hardware.usb.action.USB_STATE")) {
                boolean isConnected = intent.getExtras().getBoolean("connected");
                ToastUtils.show(context, "USB是否连接: " + isConnected);

                MainActivity.isUSBConnected = isConnected;

                ABSettingHelper.getInstance().updateAccessibilityService(context, MainActivity.SERVER_NAME, !isConnected);
            }
        }
    }
}