package com.mass.jush;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

public class MassNLService extends NotificationListenerService{
	private final static String TAG = "MassNLService";
	private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
	
	public static void autoAuthorise(Context context){
		Log.e(TAG, "autoAuthorise");
		ComponentName cn = new ComponentName(context.getPackageName(), MassNLService.class.getName());
		if(!isEnabled(context)){
	        String flat = Settings.Secure.getString(context.getContentResolver(),
	                ENABLED_NOTIFICATION_LISTENERS);
	        if(!TextUtils.isEmpty(flat)){
	        	flat = flat + ":" + cn.flattenToString();
	        }else{
	        	flat = cn.flattenToString();
	        }
	        Settings.Secure.putString(context.getContentResolver(), ENABLED_NOTIFICATION_LISTENERS, flat);
		}        
	}
	
    private static boolean isEnabled(Context context) {
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        Log.e(TAG, "enabled listeners: " + flat);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static void requestRebind(Context context){
    	ComponentName cn = new ComponentName(context.getPackageName(), MassNLService.class.getName());
    	PackageManager pm = context.getPackageManager();
    	
    	
    }
    
    private void tryLaunch(Context context){
    	Log.e(TAG, "tryLaunch MassEService from MassNLService.");
    	MassAssistant.tryLaunch(context);
    }
	
	@Override
	public void onNotificationPosted(StatusBarNotification arg0) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onNotificationPosted");
		tryLaunch(this);
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification arg0) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onNotificationRemoved");
		tryLaunch(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onBind intent " + intent);
		return super.onBind(intent);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.e(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	
	
}
