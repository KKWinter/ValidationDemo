package com.jumpraw.handler;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class Utils {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void NetworkForApi29(Context context) {


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){

            @Override
            public void onAvailable(@NonNull Network network) {
                Log.i("test", "onAvailable: >>>" + network.toString());
                super.onAvailable(network);
            }

            @Override
            public void onUnavailable() {
                Log.i("test", "onUnavailable: >>>>");
                super.onUnavailable();
            }
        });

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
     * 是否是主进程
     *
     * @param context context
     * @return true主进程，false非主进程
     */
    public static boolean isMainProcess(Context context) {

        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == android.os.Process.myPid()) {
                String processName = appProcess.processName;

                if (processName != null && processName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 根据手机的分辨率从dp转成为px
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * base64转drawable
     */
    public static BitmapDrawable getDrawable(Context context, String base64Str) {
        byte[] bytes = Base64.decode(base64Str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

}
