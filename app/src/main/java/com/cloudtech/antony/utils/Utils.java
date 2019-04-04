package com.cloudtech.antony.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by huangdong on 2018/10/29.
 * antony.huang@yeahmobi.com
 */
public class Utils {


    /**
     * 检测当前手机是否root
     *
     * @return
     */
    private static boolean isDeviceRooted = false;

    public static boolean isDeviceRooted() {
        if (isDeviceRooted) {
            return true;
        }
        if (checkDeviceDebuggable()) {
            return true;
        }//check buildTags
        if (checkSuperuserApk()) {
            return true;
        }//Superuser.apk
        if (checkRootWhichSU()) {
            return true;
        }//find su use 'which'
        if (checkRootPathSU()) {
            return true;
        }//find su in some path


        return false;
    }

    public static boolean checkSuperuserApk() {
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                CTLog.i("Utils", "/system/app/Superuser.apk exist");
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }


    public static boolean checkRootPathSU() {
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    CTLog.i("Utils", "find su in : " + kSuSearchPaths[i]);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<String> executeCommand(String[] shellCmd) {
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec(shellCmd);
        } catch (Exception e) {
            return null;
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
                CTLog.i("Utils", "–> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CTLog.i("Utils", "–> Full response was: " + fullResponse);
        return fullResponse;
    }


    public static boolean checkRootWhichSU() {
        String[] strCmd = new String[]{"/system/bin/which", "su"};
        ArrayList<String> execResult = executeCommand(strCmd);
        if (execResult != null && execResult.size() > 0) {
            CTLog.i("Utils", "execResult=" + execResult.toString());
            return true;
        } else {
            CTLog.i("Utils", "execResult=null");
            return false;
        }
    }

    public static boolean checkDeviceDebuggable() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            CTLog.i("Utils", "buildTags=" + buildTags);
            return true;
        }
        return false;
    }


}
