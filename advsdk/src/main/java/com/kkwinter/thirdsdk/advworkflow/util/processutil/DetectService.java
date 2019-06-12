package com.kkwinter.thirdsdk.advworkflow.util.processutil;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class DetectService extends AccessibilityService {
    private static String a;
    private static DetectService b = null;

    public DetectService() {
    }

    public void onAccessibilityEvent(AccessibilityEvent var1) {
        if (var1.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            a = var1.getPackageName().toString();
        }

    }

    public void onInterrupt() {
    }
}
