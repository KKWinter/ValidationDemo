package com.phone.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by huangdong on 18/5/15.
 * antony.huang@yeahmobi.com
 */
public class DetectionService extends AccessibilityService {

    final static String TAG = "DetectionService";

    static String foregroundPackageName;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY_COMPATIBILITY; // 根据需要返回不同的语义值
    }


    /**
     * 重载辅助功能事件回调函数，对窗口状态变化事件进行处理
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            /*
             * 如果 与 DetectionService 相同进程，直接比较 foregroundPackageName 的值即可
             * 如果在不同进程，可以利用 Intent 或 bind service 进行通信
             */
            foregroundPackageName = event.getPackageName().toString();

            Log.i(TAG, "onAccessibilityEvent: >>>>>>" + foregroundPackageName);


            if ("com.android.chrome".equals(foregroundPackageName)) {

                AccessibilityNodeInfo nodeInfo = event.getSource();

                findTextByNode(nodeInfo);
            }

            /*
             * 基于以下还可以做很多事情，比如判断当前界面是否是 Activity，是否系统应用等，
             */
            ComponentName cName = new ComponentName(event.getPackageName().toString(), event.getClassName().toString());

            String className = event.getClassName().toString();

            Log.i(TAG, "onAccessibilityEvent: ====" + className);

        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }


    private void findTextByNode(final AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {

            //节点匹配
            if (nodeInfo.getText() != null) {
                final String text = nodeInfo.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {

                    Log.i(TAG, "findTextByNode: >>>>>" + text);

                }
            }

            //节点遍历
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                AccessibilityNodeInfo viewInfo = nodeInfo.getChild(i);
                findTextByNode(viewInfo);
            }
        }
    }

}
