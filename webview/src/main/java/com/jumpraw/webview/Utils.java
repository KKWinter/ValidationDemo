package com.jumpraw.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebView;

import com.jumpraw.webview.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class Utils {

    /**
     * 根据部分特征参数设备信息来判断是否为模拟器
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }


    //获取NetworkInfo [实时获取，不可缓存]
    private static NetworkInfo getNetworkInfo(Context context) {
        NetworkInfo networkInfo = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (context.checkCallingOrSelfPermission(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkInfo;
    }


    /**
     * 检查网络链接
     */
    public static boolean isNetworkEnable(Context context) {
        try {
            NetworkInfo networkInfo = getNetworkInfo(context);
            return networkInfo != null && networkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 获取网络类型
     */
    public static int getNetworkType(Context context) {
        try {
            NetworkInfo networkInfo = getNetworkInfo(context);
            return networkInfo != null ? networkInfo.getType() : ConnectivityManager.TYPE_DUMMY;
        } catch (Exception e) {
            return ConnectivityManager.TYPE_DUMMY;
        }
    }


    //获取packageInfo
    private static PackageInfo packageInfo;

    private static PackageInfo getPackageInfo(Context context) {
        try {
            if (packageInfo == null) {
                PackageManager manager = context.getPackageManager();
                packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


    /**
     * 获取应用包名
     */

    public static String getAppPackageName(Context context) {
        try {
            PackageInfo packageInfo = getPackageInfo(context);
            return packageInfo != null ? packageInfo.packageName : "local";
        } catch (Exception e) {
            return "local";
        }
    }


    /**
     * 获取应用版本号
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = getPackageInfo(context);
            return packageInfo != null ? packageInfo.versionCode : 0;
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 获取应用版本名称
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = getPackageInfo(context);
            return packageInfo != null ? packageInfo.versionName : "local";
        } catch (Exception e) {
            return "local";
        }
    }


    /**
     * 获取Android Id
     */

    private static String androidID;

    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        try {
            if (TextUtils.isEmpty(androidID)) {
                androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidID;
    }


    /**
     * 获取手机IMEI
     */
    private static String imei;

    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        try {
            if (TextUtils.isEmpty(imei)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (context.checkCallingOrSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        imei = telephonyManager != null ? telephonyManager.getImei() : "";
                    } else {
                        imei = telephonyManager != null ? telephonyManager.getDeviceId() : "";

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }


    /**
     * 获取icc，ISO标准的国家码, SIM卡国家代码
     */
    public static String getNetworkCountryIso(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager != null ? telephonyManager.getNetworkCountryIso() : " ";
    }


    /**
     * 获取运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager != null ? telephonyManager.getNetworkOperatorName() : null;
    }


    // 获取mcc/mnc
    private static String networkOperator;

    private static String getNetworkOperator(Context context) {
        try {
            if (TextUtils.isEmpty(networkOperator)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    networkOperator = telephonyManager.getNetworkOperator();
                    if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA && telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
                        networkOperator = telephonyManager.getSimOperator();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkOperator;
    }


    /**
     * 获取mcc，移动设备国家地区代码
     */
    private static String mcc;

    public synchronized static String getMcc(Context context) {
        try {
            if (TextUtils.isEmpty(mcc)) {
                String networkOperator = getNetworkOperator(context);
                if (!TextUtils.isEmpty(networkOperator)) {
                    int mncPortionLength = Math.min(3, networkOperator.length());
                    mcc = networkOperator.substring(0, mncPortionLength);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mcc;
    }


    /**
     * 获取mnc，指移动网络号码，用于识别移动客户所属的移动网络
     * <p>
     * 00, "CHINA MOBILE", "CN" 中国移动
     * 01, "CHN-CUGSM", "CN" 中国联通
     * 02, "CHINA MOBILE", "CN" 中国移动 （TD）
     * 03, "CHINA TELECOM", "CN" 中国电信 [3]
     */
    private static String mnc = "";

    public synchronized static String getMnc(Context context) {
        try {
            if (TextUtils.isEmpty(mnc)) {
                String networkOperator = getNetworkOperator(context);
                if (!TextUtils.isEmpty(networkOperator)) {
                    int mncPortionLength = Math.min(3, networkOperator.length());
                    mnc = networkOperator.substring(mncPortionLength);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mnc;
    }


    /**
     * 获取已经安装的用户应用列表
     */
    public static List<String> getInstalledApps(Context context) {
        List<String> alist = new ArrayList<>();

        try {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> list = pm.getInstalledApplications(0);
            for (ApplicationInfo applicationInfo : list) {
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {   //用户应用
                    String packageName = applicationInfo.packageName;
                    alist.add(packageName);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return alist;
    }


    /**
     * 拼接url的参数
     *
     * @param stringBuilder url
     * @param params        参数集合
     */
    public static void appendUrlParameter(StringBuilder stringBuilder, Map<String, String> params) {
        appendUrlParameter(stringBuilder, params, true);
    }

    private static void appendUrlParameter(StringBuilder stringBuilder, Map<String, String> params, boolean isFirstParams) {
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                continue;
            }

            if (isFirstParams) {
                isFirstParams = false;
                stringBuilder.append("?");
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(urlEncodeUTF8(key));
            stringBuilder.append("=");
            stringBuilder.append(urlEncodeUTF8(value));
        }
    }

    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }


    /**
     * 解析json对象
     *
     * @param json obj
     * @param keys key
     * @return 字符串
     */
    public static String optStringHelper(JSONObject json, String... keys) {
        if (json == null) {
            return null;
        }

        for (int i = 0; i < keys.length - 1; i++) {
            json = json.optJSONObject(keys[i]);
            if (json == null) {
                return null;
            }
        }

        return json.optString(keys[keys.length - 1]);
    }

    /**
     * 解析json字符串数组对象
     *
     * @param json obj
     * @param keys key
     * @return 字符串集合
     */
    public static List<String> optStringArrayHelper(JSONObject json, String... keys) {
        List<String> list = new ArrayList<>();

        if (json == null) {
            return list;
        }

        for (int i = 0; i < keys.length - 1; i++) {
            json = json.optJSONObject(keys[i]);
            if (json == null) {
                return list;
            }
        }

        JSONArray jsonArray = json.optJSONArray(keys[keys.length - 1]);
        if (jsonArray == null) {
            return list;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.optString(i));
        }
        return list;


    }


    private static final String GP_HOST = "play.google.com";
    private static final String GP_MARKET_SCHEMA = "market";

    /**
     * 判断是否是目标url，googleplay或deeplink地址
     *
     * @param url url
     * @return true/false
     */
    public static boolean isTragetUrl(String url) {
        return !TextUtils.isEmpty(url) && (isGooglePlayUrl(url) || isDeepLinkUrl(url));
    }

    //检查是不是gp地址
    public static boolean isGooglePlayUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            return GP_MARKET_SCHEMA.equalsIgnoreCase(uri.getScheme())
                    || GP_HOST.equalsIgnoreCase(uri.getHost());
        } catch (Exception e) {
            return false;
        }
    }

    //检查是不是deeplink地址
    public static boolean isDeepLinkUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            return !"http".equalsIgnoreCase(uri.getScheme())
                    && !"https".equalsIgnoreCase(uri.getScheme());
        } catch (Exception e) {
            return false;
        }

    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 是否安装googleplay
     *
     * @return 是否安装gp
     */
    public static boolean isGooglePlayInstalled(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo("com.android.vending", PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 获取指定时间字符串
     *
     * @return 时间字符串
     */
    public static String getTimeStr(long time) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            Date date = new Date(time);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * current 是否 last 后一天
     *
     * @param currentTime 当前时间戳
     * @param lastTime    上一次的时间戳
     * @return tue/false
     */
    public static boolean isNextDay(long currentTime, long lastTime) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date currentDate = new Date(currentTime);
            Date lastDate = new Date(lastTime);
            int result = simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(lastDate));
            return result > 0;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 获取手机版本信息
     */
    private static String displayID;

    public static String getDisplayID() {
        try {
            if (TextUtils.isEmpty(displayID)) {
                @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
                Method method = Build.class.getDeclaredMethod("getString", String.class);
                method.setAccessible(true);
                displayID = (String) method.invoke(new Build(), "ro.build.display.id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return displayID;
    }


    /**
     * 获取useragent（base64加密之后）
     */
    private static String base64UerAgent;

    public static String getUserAgentStr(Context context) {
        if (TextUtils.isEmpty(base64UerAgent)) {
            context = createCredentialProtectedStorageContext(context);
            if (context != null) {
                WebView webView = null;
                try {
                    webView = new WebView(context);
                    String ua = webView.getSettings().getUserAgentString();
                    base64UerAgent = Base64.encodeToString(ua.getBytes(), Base64.DEFAULT);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (webView != null) {
                        webView.destroy();
                    }
                }
            }
        }
        return base64UerAgent;
    }

    /**
     * 判断当前应用是否是系统应用
     *
     * @param context context
     * @return 是否系统应用
     */
    public static boolean isSystemApp(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return (info != null) && (info.applicationInfo != null) &&
                    ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
        } catch (Throwable e) {
            return false;
        }
    }


    /**
     * 获取可以在系统进程使用的context
     *
     * @return context: 不为空，可以在系统进程使用；为空，不能使用
     */
    public static Context createCredentialProtectedStorageContext(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && context.isDeviceProtectedStorage()) {
                Class cls = Class.forName("android.content.Context");
                Method method = cls.getDeclaredMethod("createCredentialProtectedStorageContext");
                context = (Context) method.invoke(context);

                //处理之后，还是DeviceProtectedStorage，不能用
                if (context.isDeviceProtectedStorage()) {
                    context = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return context;
    }


    /**
     * hookwebview，可以在系统进程中使用
     */
    public static void hookWebView() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> factoryClass = Class.forName("android.webkit.WebViewFactory");
            Field field = factoryClass.getDeclaredField("sProviderInstance");
            field.setAccessible(true);
            Object sProviderInstance = field.get(null);
            if (sProviderInstance != null) {
                return;
            }

            Method getProviderClassMethod;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
            } else {
                return;
            }
            getProviderClassMethod.setAccessible(true);
            Class<?> factoryProviderClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
            @SuppressLint("PrivateApi")
            Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> delegateConstructor = delegateClass.getDeclaredConstructor();
            delegateConstructor.setAccessible(true);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                //低于Android O版本
                Constructor<?> providerConstructor = factoryProviderClass.getConstructor(delegateClass);
                providerConstructor.setAccessible(true);
                sProviderInstance = providerConstructor.newInstance(delegateConstructor.newInstance());
            } else {
                //O版本以上
                Method staticFactory = factoryProviderClass.getMethod("create", delegateClass);
                staticFactory.setAccessible(true);
                sProviderInstance = staticFactory.invoke(null, delegateConstructor.newInstance());
            }

            if (sProviderInstance != null) {
                field.set("sProviderInstance", sProviderInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否adb调试模式
     *
     * @param context context
     * @return adb调试模式是否开启
     */
    private static boolean isAdbEnabled(Context context) {
        if (BuildConfig.DEBUG) {
            return false;
        }
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0;
    }


    /**
     * 判断设备是否使用代理上网
     *
     * @return 设备是否代理上网
     */
    private static boolean isWifiProxy() {
        if (BuildConfig.DEBUG) {
            return false;
        }
        return !TextUtils.isEmpty(System.getProperty("http.proxyHost")) || !TextUtils.isEmpty(System.getProperty("http.proxyPort"));
    }

    /**
     * 检测环境安全性，adb调试是否开启、wifi代理是否开启
     *
     * @param context context
     * @return true安全，false不安全
     */
    public static boolean isSafty(Context context) {
        return !isAdbEnabled(context) && !isWifiProxy();
    }


    /**
     * 获取jumpraw目录下，moduleName开头的文件
     *
     * @param context    context
     * @param moduleName 模块名
     */
    public static void removeDexFile(Context context, String moduleName) {
        try {
            File dexDir = context.getDir("libs", Context.MODE_PRIVATE);
            File parentFile = new File(dexDir, "jumpraw");

            if (parentFile.exists() && parentFile.isDirectory()) {
                for (File file : parentFile.listFiles()) {
                    if (file.getName().startsWith(moduleName)) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
