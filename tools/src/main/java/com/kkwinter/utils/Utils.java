package com.kkwinter.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static java.lang.System.currentTimeMillis;

public class Utils {

    /**
     * 获取当前时区
     *
     * @return 时区
     */
    public static String getTimeZone() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("Z");
        format.setTimeZone(TimeZone.getDefault());
        return format.format(currentTimeMillis());
    }

    /**
     * 获取当前时间
     *
     * @return 时：分：秒
     */
    public static String getTimeStr() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 检查网络链接
     *
     * @param context 环境变量
     * @return 是否连接
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
     *
     * @param context 环境变量
     * @return 网络类型
     */
    public static int getNetworkType(Context context) {
        try {
            NetworkInfo networkInfo = getNetworkInfo(context);
            return networkInfo != null ? networkInfo.getType() : ConnectivityManager.TYPE_DUMMY;
        } catch (Exception e) {
            return ConnectivityManager.TYPE_DUMMY;
        }
    }

    /**
     * 获取ip地址
     *
     * @param context 环境变量
     * @return ip地址
     */
    public static String getIpAddress(Context context) {
        int netType = getNetworkType(context);

        if (netType == ConnectivityManager.TYPE_WIFI) {            //WIFI
            return getWifiIpAddress(context);
        } else if (netType == ConnectivityManager.TYPE_MOBILE) {   //MOBILE
            return getLocalIpAddress();
        } else {
            return "127.0.0.1";
        }
    }

    //使用wifi
    private static String getWifiIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            if (context.checkCallingOrSelfPermission(ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                wifiInfo = wifiManager != null ? wifiManager.getConnectionInfo() : null;
            }

            if (wifiInfo != null) {
                return intToIp(wifiInfo.getIpAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    //使用GPRS
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    // 获取NetworkInfo
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
     * 获取应用包名
     * @param context 环境变量
     * @return 包名
     */
    public static String getAppPackageName(Context context) {
        String pkgName = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            pkgName = info.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pkgName;
    }

    /**
     * 获取应用版本号
     *
     * @param context 环境变量
     * @return 版本号
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    /**
     * 获取android id
     *
     * @param context 环境变量
     * @return aid
     */
    public static String getAndroidId(Context context) {
        String androidId = "";
        try {
            androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidId;
    }


    /**
     * 获取手机IMEI
     *
     * @param context 环境变量
     * @return imei
     */
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (context.checkCallingOrSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                imei = telephonyManager != null ? telephonyManager.getDeviceId() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取国家代码
     *
     * @param context context
     * @return 国家代码
     */
    public static String getNetworkCountryIso(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager != null ? telephonyManager.getNetworkCountryIso() : null;
    }


    /**
     * 是否安装googleplay
     *
     * @param context context
     * @return 是否安装gp
     */
    public static boolean isGooglePlayInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo("com.android.vending", PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private static String mccCache = "";

    /**
     * 获取mcc
     *
     * @param context context
     * @return mcc
     */
    public synchronized static String getMcc(Context context) {
        if (!TextUtils.isEmpty(mccCache)) {
            return mccCache;
        }
        String networkOperator = getNetworkOperatorForAdRequestUrl(context);
        if (null == networkOperator || TextUtils.isEmpty(networkOperator) || networkOperator.equals("null")) {
            mccCache = "";
        } else {
            int mncPortionLength = Math.min(3, networkOperator.length());
            mccCache = networkOperator.substring(0, mncPortionLength);
        }
        return mccCache;
    }


    private static String mncCache = "";

    /**
     * 获取mnc
     *
     * @param context context
     * @return mnc
     */
    public synchronized static String getMnc(Context context) {
        if (!TextUtils.isEmpty(mncCache)) {
            return mncCache;
        }

        String networkOperator = getNetworkOperatorForAdRequestUrl(context);
        if (null == networkOperator || TextUtils.isEmpty(networkOperator) || networkOperator.equals("null")) {
            mncCache = "";
        } else {
            int mncPortionLength = Math.min(3, networkOperator.length());
            mncCache = networkOperator.substring(mncPortionLength);
        }
        return mncCache;
    }


    private static String getNetworkOperatorForAdRequestUrl(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return "";
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA
                && telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            networkOperator = telephonyManager.getSimOperator();
        }
        return networkOperator;
    }


    /**
     * 获取运营商名称
     *
     * @param context context
     * @return 运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager != null ? telephonyManager.getNetworkOperatorName() : null;
    }


    /*
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
        } else {
            for (int i = 0; i < keys.length - 1; i++) {
                json = json.optJSONObject(keys[i]);
                if (json == null) {
                    return null;
                }
            }

            return json.optString(keys[keys.length - 1]);
        }
    }

    /**
     * 解析json数组对象
     *
     * @param json obj
     * @param keys key
     * @return 字符串集合
     */
    public static List<String> optStringArrayHelper(JSONObject json, String... keys) {
        List<String> list = new ArrayList<>();

        if (json == null) {
            return list;
        } else {
            for (int i = 0; i < keys.length - 1; i++) {
                json = json.optJSONObject(keys[i]);
                if (json == null) {
                    return list;
                }
            }

            JSONArray jsonArray = json.optJSONArray(keys[keys.length - 1]);
            if (jsonArray == null) {
                return list;
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.optString(i));
                }
                return list;
            }
        }
    }


    /**
     * 获取view的所有子view的集合
     *
     * @param view view
     * @return view集合
     */
    public static List<View> getAllChildsList(View view) {
        List<View> viewList = new ArrayList<>();
        viewList.add(view);
        if (view instanceof ViewGroup) {
            getAllChilds((ViewGroup) view, viewList);
        }
        return viewList;
    }

    private static void getAllChilds(ViewGroup vg, List<View> res) {

        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            res.add(v);
            if (v instanceof ViewGroup) {
                getAllChilds((ViewGroup) v, res);
            }
        }
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


    /*
     * 不以http、https、market开头的url
     */
    public static boolean isWebViewPossibleCrashDeepLink(String url) {
        //According user crash log, 4.04 webview crash sometimes.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        }

        if (TextUtils.isEmpty(url)) {
            return false;
        }

        try {
            String scheme = Uri.parse(url).getScheme();
            return !GP_MARKET_SCHEMA.equalsIgnoreCase(scheme)
                    && !"http".equalsIgnoreCase(scheme)
                    && !"https".equalsIgnoreCase(scheme);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 拼接时间戳
     *
     * @param res               追踪链接集合
     * @param isUpdateTimeStamp 是否拼接时间戳
     * @param isNormal          是否正常广告曝光
     * @return 处理过的链接集合
     */
    public static List<String> appendTS(List<String> res, boolean isUpdateTimeStamp, boolean isNormal) {
        List<String> list = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        if (isUpdateTimeStamp) {
            if (isNormal) {
                currentTime = currentTime + ((((currentTime % 1000) / 100) % 2) == 0 ? 0 : 100);
            } else {
                currentTime = currentTime + ((((currentTime % 1000) / 100) % 2) == 0 ? 100 : 0);
            }
        }

        for (int i = 0; i < res.size(); i++) {
            String str = res.get(i);
            str = str + "&ts=" + currentTime;
            list.add(str);
        }
        return list;
    }


    /**
     * 获取useragent（base64加密之后）
     */
    private static String base64UerAgent;

    public static String getUserAgentStr(Context context) {
        if (TextUtils.isEmpty(base64UerAgent)) {
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
        return base64UerAgent;
    }


    /**
     * 往剪切板写入字符串
     *
     * @param context context
     * @param string  淘口令之类
     */
    public static void setClipboard(Context context, String string) {
        WLog.i("ClipboardManager", "copy to clipboard: >>" + string);

        if (TextUtils.isEmpty(string)) {
            return;
        }

        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", string);
            // 将ClipData内容放到系统剪贴板里。
            if (cm != null) {
                cm.setPrimaryClip(mClipData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取当前版本
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }



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
                WLog.i("Utils", "/system/app/Superuser.apk exist");
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
                    WLog.i("Utils", "find su in : " + kSuSearchPaths[i]);
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
                WLog.i("Utils", "–> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WLog.i("Utils", "–> Full response was: " + fullResponse);
        return fullResponse;
    }


    public static boolean checkRootWhichSU() {
        String[] strCmd = new String[]{"/system/bin/which", "su"};
        ArrayList<String> execResult = executeCommand(strCmd);
        if (execResult != null && execResult.size() > 0) {
            WLog.i("Utils", "execResult=" + execResult.toString());
            return true;
        } else {
            WLog.i("Utils", "execResult=null");
            return false;
        }
    }

    public static boolean checkDeviceDebuggable() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            WLog.i("Utils", "buildTags=" + buildTags);
            return true;
        }
        return false;
    }


}
