package com.mass.jush;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * Created by zhj_7 on 2016/11/18.
 */

public class MassAssistant {
    private final static String CFG = MassService.class.getName() + ".cos";
    private final static int DEFAULT_LOCK = 5;
    private final static long ONE_DAY = 24*60*60*1000;

    public static void check(Context context, String action){
        if(action.equals(Intent.ACTION_USER_PRESENT)
                || action.equals(Intent.ACTION_BOOT_COMPLETED)
                || action.equals(Intent.ACTION_SCREEN_OFF)
                || action.equals(Intent.ACTION_SCREEN_ON)){
            tryLaunch(context);
        }else if(action.equals(Intent.ACTION_PACKAGE_ADDED)
                || action.equals(Intent.ACTION_PACKAGE_REMOVED)){
        	tryLaunch(context);
        }else if(action.equals(Intent.ACTION_POWER_CONNECTED)
                || action.equals(Intent.ACTION_POWER_DISCONNECTED)){
        	tryLaunch(context);
        }else if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL)
        		|| action.equals(Intent.ACTION_DIAL)
        		|| action.equals(Intent.ACTION_CALL)){
        	tryLaunch(context);
        }else if(action.equals(Intent.ACTION_HEADSET_PLUG)){
        	tryLaunch(context);
        }
    }

    private static long getLastCheckPoint(Context context){
        SharedPreferences spf = context.getSharedPreferences(CFG, Context.MODE_PRIVATE);
        return spf.getLong("LCKP", 0);
    }

    private static void setLastCheckPoint(Context context, long value){
        SharedPreferences spf = context.getSharedPreferences(CFG, Context.MODE_PRIVATE);
        spf.edit().putLong("LCKP", value).commit();
    }
 
    private static long getRecordedPeriod(Context context){
        SharedPreferences spf = context.getSharedPreferences(CFG, Context.MODE_PRIVATE);
        return spf.getLong("RDP", 0);
    }

    private static void setRecordedPreiod(Context context, long value){
        SharedPreferences spf = context.getSharedPreferences(CFG, Context.MODE_PRIVATE);
        spf.edit().putLong("RDP", value).commit();
    }

    public static void tryLaunch(Context context){   
    	PackageManager pm = context.getPackageManager();
    	int lock = DEFAULT_LOCK;	//DEFAULT_LOCK = 5
    	try {
			ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
	        Bundle meta = ai.metaData;
	        if(meta!=null && meta.containsKey("ww_lock")){
	        	lock = meta.getInt("ww_lock");
	        }
		} catch (NameNotFoundException e) {
		}

    	if(lock==0){
    		//没有保护期，直接启动service
    		load(context);
    	}else{
            long rp = getRecordedPeriod(context);
            
            if(rp>=lock*ONE_DAY){
            	//保护期结束，启动service
            	load(context);
            }else{
            	//上次记录显示还在保护期内，记录检测时间点，时长；判断本次检测是否已经过保护期
                long currMillis = System.currentTimeMillis();               
                
                //读取上次检测时间点，保存本次检测时间点。
                long lastcp = getLastCheckPoint(context);
                setLastCheckPoint(context, currMillis);
                
	            if(lastcp==0){
	            	//初次运行，读不到上次检测时间点，直接结束进程。
	            	close();
	            }else{	            	
	            	long gap = currMillis - lastcp;
	            	if(gap>0 && gap < ONE_DAY){
	            		//本次检测时间点在上次检测点后面，并且两次间隔少于一天，间隔时间有效。
	            		long totalgap = gap + rp;
	            		setRecordedPreiod(context, totalgap);
	            		if(totalgap >= lock*ONE_DAY){
	            			//本次检测时，累计运行时间已经超过保护期，保护期结束，启动service
	            			load(context);
	            		}else{
	            			//仍然在保护期内，结束检测过程，结束进程。
	            			close();
	            		}
	            	}else{
	            		//本次检测时间点在上次监测点前面，或者两次间隔超过一天，间隔时间无效，结束进程
	            		close();
	            	}
	            }
            }
    	}
    }
    
    private static void load(Context context){
        Intent intent = new Intent(context, MassEService.class);
        
        context.startService(intent); 
    }
    
    private static void close(){
    	android.os.Process.killProcess(android.os.Process.myPid());
    }
}
