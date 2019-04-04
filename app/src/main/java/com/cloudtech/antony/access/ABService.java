package com.cloudtech.antony.access;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.cloudtech.antony.utils.CTLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huangdong on 18/8/21.
 * antony.huang@yeahmobi.com
 */
public class ABService extends AccessibilityService {

    private static ABServiceListener mABServiceListener;
    private String nativePkg = "com.cloudtech.antony";
    private String amazonPkg = "in.amazon.mShop.android.shopping";
    private List<String> pkgList = new ArrayList<>();

    public static void setABServiceListener(ABServiceListener abServiceListener) {
        mABServiceListener = abServiceListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mABServiceListener != null) {
            mABServiceListener.onCreate();
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        if (mABServiceListener != null) {
            mABServiceListener.onServiceConnected();
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的


        CTLog.d("original>>>>>>" + event);



//        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//        if (mABServiceListener != null) {
//            mABServiceListener.onAccessibilityEvent(event, nodeInfo);
//        }
//
//
//        if (amazonPkg.equals(event.getPackageName())) {
//            checkAndLaunchHitApp(event, nodeInfo, System.currentTimeMillis());
//        }
    }

    @Override
    public void onInterrupt() {
        if (mABServiceListener != null) {
            mABServiceListener.onInterrupt();
        }
    }


    private long lastTime = 0;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private AccessibilityNodeInfo lastAccessibilityNodeInfo;


    private synchronized void checkAndLaunchHitApp(AccessibilityEvent event, AccessibilityNodeInfo nodeInfo, long currentTime) {
        if (currentTime - 10 * 1000 < lastTime) {
            return;
        }
        lastTime = currentTime;

        if (amazonPkg.equals(event.getPackageName())) {

            findViewByNode(nodeInfo);

        }

    }

    private void findViewByNode(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            if (nodeInfo.getText() != null) {
                String text = nodeInfo.getText().toString().trim();

                if (!TextUtils.isEmpty(text)) {

                    if (text.contains("Features & details")) {
                        CTLog.d("nodeInfo text >>>>>>" + text);
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }


                    if (text.contains("विशेषताएं और विवरण") && text.contains("और देखें")) {
                        CTLog.d("nodeInfo text >>>>>>" + text);
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }

                }
            }

            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                AccessibilityNodeInfo viewInfo = nodeInfo.getChild(i);
                findViewByNode(viewInfo);
            }
        }
    }


    private void findAndSendNode(AccessibilityNodeInfo info) {

        if (info != null) {


//            ref=sr_1_1?s=electronics&ie=UTF8&qid=1536653577&sr=1-1&keywords=redmi#

//            ref%3Das_li_tl?linkCode=as2&linkId=db4ce0d4471b8a342fc93c4b7b221ebf&creativeASIN=B077PWBC7J&dplnk=Y&tag=cloudmobi20-21&ie=UTF8&creative=24630&camp=3638#


//            List<AccessibilityNodeInfo> textNodeInfo = info.findAccessibilityNodeInfosByText("CLOSE");
//            CTLog.d(">>>>>>>" + textNodeInfo);
//
//
//            List<AccessibilityNodeInfo> nodeInfoList1 = info.findAccessibilityNodeInfosByViewId("com.cloudtech.antony:id/check");
//            CTLog.d("findAccessibilityNodeInfosByText =======" + nodeInfoList1);
//
//
//
//            List<AccessibilityNodeInfo> nodeInfoList2 = info.findAccessibilityNodeInfosByViewId("com.cloudtech.antony:id/text");
//            CTLog.d("findAccessibilityNodeInfosByText =======" + nodeInfoList2);


//            List<AccessibilityNodeInfo> nodeInfoList3 = info.findAccessibilityNodeInfosByViewId("in.amazon.mShop.android.shopping:id/item_title");
//            CTLog.d("findAccessibilityNodeInfosByText3  >>>>" + nodeInfoList3.size());
//            for (AccessibilityNodeInfo nodeInfo : nodeInfoList3) {
//                CharSequence ch = nodeInfo.getText();
//                if (ch != null) {
//                    CTLog.d("item_title >>>" + ch.toString());
//
//                }
//            }

            List<AccessibilityNodeInfo> nodeInfoList4 = info.findAccessibilityNodeInfosByViewId("in.amazon.mShop.android.shopping:id/action_bar_itself");
            CTLog.d("findAccessibilityNodeInfosByText4  >>>>" + nodeInfoList4);
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList4) {
                CharSequence ch = nodeInfo.getText();
                if (ch != null) {
                    CTLog.d("item_title >>>" + ch.toString());

                }
            }


//            if (nodeInfoList != null) {
//                for (AccessibilityNodeInfo accessibilityNodeInfo : nodeInfoList) {
//                    if (accessibilityNodeInfo != null) {
//                        if (accessibilityNodeInfo.getClassName().equals("android.widget.Button") && accessibilityNodeInfo.isEnabled())
//                            accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    }
//                }
//            }

        }


    }
}

