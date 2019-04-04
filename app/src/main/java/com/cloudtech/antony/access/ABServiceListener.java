package com.cloudtech.antony.access;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by huangdong on 18/8/31.
 * antony.huang@yeahmobi.com
 */
public interface ABServiceListener {

    void onCreate();

    void onServiceConnected();

    void onAccessibilityEvent(AccessibilityEvent event, AccessibilityNodeInfo nodeInfo);

    void onInterrupt();
}
