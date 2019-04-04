package com.cloudtech.antony.access;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.cloudtech.antony.utils.CTLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ABSettingHelper {

    private static final String ENABLED_ACCESSIBILITY_SERVICES_SEPARATOR = ":";
    private static ABSettingHelper abSettingHelper;
    private List<ABServiceListener> listenerList = new ArrayList<>();

    private ABSettingHelper() {

    }

    public static ABSettingHelper getInstance() {
        if (abSettingHelper == null) {
            synchronized (ABSettingHelper.class) {
                if (abSettingHelper == null) {
                    abSettingHelper = new ABSettingHelper();
                }
            }
        }
        return abSettingHelper;
    }


    public void register(Context context) {
        ABSettingObserver.getInstance().registerObserver(context);
//        USBReceiver.getInstance().registerReceiver(context);
    }

    public void unRegister(Context context) {
        ABSettingObserver.getInstance().unregisterObserver(context);
//        USBReceiver.getInstance().registerReceiver(context);
    }

    public void setABServiceListener(ABServiceListener abServiceListener) {
        if (!listenerList.contains(abServiceListener)) {
            listenerList.add(abServiceListener);
        }
        ABService.setABServiceListener(defaultABServiceListener);
    }


    private ABServiceListener defaultABServiceListener = new ABServiceListener() {
        @Override
        public void onCreate() {
            for (ABServiceListener abServiceListener : listenerList) {
                abServiceListener.onCreate();
            }
        }

        @Override
        public void onServiceConnected() {
            for (ABServiceListener abServiceListener : listenerList) {
                abServiceListener.onServiceConnected();
            }
        }

        @Override
        public void onAccessibilityEvent(AccessibilityEvent event, AccessibilityNodeInfo nodeInfo) {
            for (ABServiceListener abServiceListener : listenerList) {
                abServiceListener.onAccessibilityEvent(event, nodeInfo);
            }
        }


        @Override
        public void onInterrupt() {
            for (ABServiceListener abServiceListener : listenerList) {
                abServiceListener.onInterrupt();
            }
        }
    };


    /**
     * 更新辅助功能开关
     *
     * @param serviceName          serviceName
     * @param accessibilityEnabled on/off
     */
    public void updateAccessibilityService(Context context, String serviceName, boolean accessibilityEnabled) {
        try {
            Set<ComponentName> enabledServices = getEnabledServicesFromSettings(context);
            CTLog.d("enabledServices: " + enabledServices);

            ComponentName toggledService = ComponentName.unflattenFromString(serviceName);
            CTLog.d("toggledService: " + toggledService);
            if (toggledService == null) {
                return;
            }

            boolean isContains = enabledServices.contains(toggledService);
            if (accessibilityEnabled) {   //打开，加到集合
                if (!isContains) {
                    enabledServices.add(toggledService);
                }
            } else {                      //关闭，移出集合
                if (isContains) {
                    enabledServices.remove(toggledService);
                }
            }

            // Update the enabled services setting.
            StringBuilder enabledServicesBuilder = new StringBuilder();
            // Keep the enabled services even if they are not installed since we
            // have no way to know whether the application restore process has
            // completed. In general the system should be responsible for the
            // clean up not settings.
            for (ComponentName enabledService : enabledServices) {
                enabledServicesBuilder.append(enabledService.flattenToString());
                enabledServicesBuilder.append(ENABLED_ACCESSIBILITY_SERVICES_SEPARATOR);
            }
            final int enabledServicesBuilderLength = enabledServicesBuilder.length();
            if (enabledServicesBuilderLength > 0) {
                enabledServicesBuilder.deleteCharAt(enabledServicesBuilderLength - 1);
            }

            CTLog.d("accessibilityEnabled: " + accessibilityEnabled);
            CTLog.d("final enabledServices: " + enabledServicesBuilder.toString());

            Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 1);
            Settings.Secure.putString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, enabledServicesBuilder.toString());
            CTLog.d("updateAccessibilityService success");
        } catch (Exception e) {
            CTLog.d("updateAccessibilityService failed: " + e.getLocalizedMessage());
        }
    }


    /**
     * 获取已经打开辅助功能的列表
     *
     * @return the set of enabled accessibility services. If there are not services
     * it returned the unmodifiable {@link Collections#emptySet()}.
     */
    private Set<ComponentName> getEnabledServicesFromSettings(Context context) {
        Set<ComponentName> enabledServices = new HashSet<>();
        String enabledServicesSetting = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (enabledServicesSetting != null) {
            String[] componentNameStrings = enabledServicesSetting.split(ENABLED_ACCESSIBILITY_SERVICES_SEPARATOR);
            for (String componentNameString : componentNameStrings) {
                ComponentName enabledService = ComponentName.unflattenFromString(componentNameString);
                if (enabledService != null) {
                    enabledServices.add(enabledService);
                }
            }
        }

        return enabledServices;
    }


    /**
     * 检查辅助功能是否打开
     *
     * @param serviceName serviceName
     * @return on/off
     */
    public boolean checkAccessibilityService(Context context, String serviceName) {
        try {
            int accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
            CTLog.d("checkAccessibilityService accessibilityEnabled: " + accessibilityEnabled);

            if (accessibilityEnabled == 1) {
                Set<ComponentName> enabledServices = getEnabledServicesFromSettings(context);
                ComponentName toggledService = ComponentName.unflattenFromString(serviceName);
                if (toggledService != null && enabledServices.contains(toggledService)) {
                    CTLog.d("checkAccessibilityService: true");
                    return true;
                }
            }
        } catch (Exception e) {
            CTLog.d("Error finding setting: " + e.getMessage());
        }

        CTLog.d("checkAccessibilityService: false");
        return false;
    }

}
