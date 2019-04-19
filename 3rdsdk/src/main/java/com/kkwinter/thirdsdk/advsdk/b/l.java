package com.kkwinter.thirdsdk.advsdk.b;


import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 设备信息获取类
 */
public class l {
    private static com.kkwinter.thirdsdk.advsdk.b.l b = null;
    private static final String c = com.kkwinter.thirdsdk.advsdk.b.g.a("7KbULaQaxQbvnsYiBW4=");                //adv_power_uuid
    private static final String d = com.kkwinter.thirdsdk.advsdk.b.g.a("7KbULaQaxQbvnt42D2K10EKUuQ==");        //adv_power_machineid
    private Context e = null;
    private Boolean f = false;  //hasSu
    private boolean g = false;  //isEnableSE
    private String h = "";      //uuid
    private String i = "";      //machineid
    private String j = "";      //netType
    private String k = "";      //platformVer
    private int l = 0;          //platformSdk
    private String m = "";      //platformModel
    private String n = "";      //platformManufacturer
    private String o = "";      //platformBrand
    private String p = "";      //platformProduct
    private String q = "";      //platformDevice
    private String r = "";      //androidID
    private String s = "";      //screenDPI
    private String t = "";      //screenResolution
    private String u = "";      //serialNumber
    private String v = "";      //cpuABI
    private String w = "";      //cpuABI2
    private String x = "";      //cpuArch
    private String y = "";      //telIMEI1
    private String z = "";      //telIMEI2
    private String A = "";      //telIMSI1
    private String B = "";      //telIMSI2
    private String C = "";      //localMacAddress
    private String D = "";      //localIP
    private String E = "";      //gatewayMacAddress
    private int F = 0;          //batteryPercent
    private boolean G = true;   //isBatteryCharging
    private String H = "";      //externalSDCardPath
    private String I = "";      //hostAppDisplayName
    private String J = "";      //hostAppPackageName
    private boolean K = false;  //hostAppIsDebuggable
    private String L = "";      //hostAppVersion
    private String M = "";      //hostAppInstallSrc
    private String N = "";      //webUA
    private String O = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");     //memTotalSize    //0
    private String P = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");     //memFreeSize
    private String Q = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");     //phoneDiskTotalSize
    private String R = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");     //phoneDiskFreeSize
    private String S = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");     //externalSDCardTotalSize
    private String T = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");     //externalSDCardFreeSize


    //单例
    public static com.kkwinter.thirdsdk.advsdk.b.l a(Context var0) {
        if (b == null) {
            b = new com.kkwinter.thirdsdk.advsdk.b.l(var0);
        }
        return b;
    }


    //构造方法
    private l(Context var1) {
        this.e = var1;

        //root
        // /system/bin/su || /system/xbin/su
        this.f = (new File(com.kkwinter.thirdsdk.advsdk.b.g.a("orHbAaAQ30z/qN14H38="))).exists() || (new File(com.kkwinter.thirdsdk.advsdk.b.g.a("orHbAaAQ30zlo9o5Q3mp"))).exists();

        //selinux[]
        com.kkwinter.thirdsdk.advsdk.b.l var25 = this;
        String var2;
        String var3;
        if (VERSION.SDK_INT < 18) {
            this.g = false;
        } else {
            this.g = true;

            try {
                var2 = com.kkwinter.thirdsdk.advsdk.b.g.a("orHbAfsTwUzupN8+An+kkUKTu4JU1uE=");   // /sys/fs/selinux/enforce
                if ((new File(var2)).canRead()) {
                    var3 = com.kkwinter.thirdsdk.advsdk.b.p.a(var2);
                    var25.g = var3.equalsIgnoreCase(com.kkwinter.thirdsdk.advsdk.b.g.a("vA=="));  //1
                }
            } catch (Exception var23) {

            }
        }

        //uuid
        this.h = com.kkwinter.thirdsdk.advsdk.b.v.b(this.e, c, "");
        if (TextUtils.isEmpty(this.h)) {
            var3 = UUID.randomUUID().toString().replace(com.kkwinter.thirdsdk.advsdk.b.g.a("oA=="), "").toLowerCase();  // -
            var2 = (new SimpleDateFormat(com.kkwinter.thirdsdk.advsdk.b.g.a("9LvbC5k41gf1qd46H3mP7XQ="))).format(new Date());       // yyyyMMddhhmmssSSS
            this.h = com.kkwinter.thirdsdk.advsdk.b.e.a.a(var3 + var2);
            com.kkwinter.thirdsdk.advsdk.b.v.a(this.e, c, this.h);
        }

        //machineid
        this.i = com.kkwinter.thirdsdk.advsdk.b.v.b(this.e, d, "");
        var25 = this;


        //netType
        com.kkwinter.thirdsdk.advsdk.b.t.a var26;
        try {
            if ((var26 = com.kkwinter.thirdsdk.advsdk.b.t.a(var25.e)) == com.kkwinter.thirdsdk.advsdk.b.t.a.c) {
                var25.j = com.kkwinter.thirdsdk.advsdk.b.g.a("2ovkOw==");
            } else if (var26 == com.kkwinter.thirdsdk.advsdk.b.t.a.b) {
                var25.j = com.kkwinter.thirdsdk.advsdk.b.g.a("wI3gO5gw");
            } else if (var26 == com.kkwinter.thirdsdk.advsdk.b.t.a.d) {
                var25.j = com.kkwinter.thirdsdk.advsdk.b.g.a("wpbqN4Y=");
            } else {
                var25.j = com.kkwinter.thirdsdk.advsdk.b.g.a("w43sNw==");
            }
        } catch (Exception var22) {
            this.j = com.kkwinter.thirdsdk.advsdk.b.g.a("w43sNw==");
        }

        //设备相关
        this.k = VERSION.RELEASE;
        this.l = VERSION.SDK_INT;
        this.m = Build.MODEL.replace(com.kkwinter.thirdsdk.advsdk.b.g.a("rQ=="), "");
        this.n = Build.MANUFACTURER;
        this.o = Build.BRAND;
        this.p = Build.PRODUCT;
        this.q = Build.DEVICE;


        //androidID
        var25 = this;
        try {
            var25.r = Secure.getString(var25.e.getContentResolver(), "android_id");
        } catch (Exception var21) {
            this.r = "";
        }

        //screenDPI
        var25 = this;
        Context var27;
        try {
            StringBuilder var10001 = new StringBuilder();
            var27 = var25.e;
            new DisplayMetrics();
            var25.s = var10001.append(var27.getResources().getDisplayMetrics().densityDpi).toString();
        } catch (Exception var20) {
            this.s = "";
        }

        //screenResolution
        var25 = this;
        try {
            var25.t = com.kkwinter.thirdsdk.advsdk.b.m.a(var25.e) + com.kkwinter.thirdsdk.advsdk.b.g.a("9Q==") + com.kkwinter.thirdsdk.advsdk.b.m.b(var25.e);  // x
        } catch (Exception var19) {
            this.t = "";
        }

        //serialNumber
        var25 = this;
        try {
            Method var28;
            (var28 = Build.class.getDeclaredMethod(com.kkwinter.thirdsdk.advsdk.b.g.a("6qfWIbEH2wLx"))).setAccessible(true);
            var25.u = (String)var28.invoke(Build.class);
        } catch (Exception var18) {
            this.u = "";
        }

        //cpu
        this.v = Build.CPU_ABI;
        this.w = Build.CPU_ABI2;
        this.x = com.kkwinter.thirdsdk.advsdk.b.g.a("9fGQ");
        if (this.v.indexOf(com.kkwinter.thirdsdk.advsdk.b.g.a("7LDPROBYxFv8")) >= 0 || this.w.indexOf(com.kkwinter.thirdsdk.advsdk.b.g.a("7LDPROBYxFv8")) >= 0) {
            this.x = com.kkwinter.thirdsdk.advsdk.b.g.a("9fSW");
        }

        //telIMEI /telIMSI
        var25 = this;
        try {
            if (VERSION.SDK_INT >= 23 && var25.e.checkSelfPermission("android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_DENIED) {
                var25.y = "";
                var25.z = "";
                var25.A = "";
                var25.B = "";
            } else {
                var27 = var25.e;
                com.kkwinter.thirdsdk.advsdk.b.w.a var10000;
                com.kkwinter.thirdsdk.advsdk.b.w.a var29;
                if ((var29 = com.kkwinter.thirdsdk.advsdk.b.w.c(var25.e)) != null) {
                    var10000 = var29;
                } else if ((var29 = com.kkwinter.thirdsdk.advsdk.b.w.a(var27)) != null) {
                    var10000 = var29;
                } else if ((var29 = com.kkwinter.thirdsdk.advsdk.b.w.b(var27)) != null) {
                    var10000 = var29;
                } else if ((var29 = com.kkwinter.thirdsdk.advsdk.b.w.d(var27)) != null) {
                    var10000 = var29;
                } else {
                    (var29 = new com.kkwinter.thirdsdk.advsdk.b.w.a()).c = ((TelephonyManager)var27.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    var29.a = ((TelephonyManager)var27.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
                    var29.e = ((TelephonyManager)var27.getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType();
                    var10000 = var29;
                }

                com.kkwinter.thirdsdk.advsdk.b.w.a var30 = var10000;

                try {
                    var25.y = var30.c;
                    if (var25.y == null) {
                        var25.y = "";
                    }
                } catch (Exception var17) {
                    var25.y = "";
                }

                try {
                    var25.z = var30.d;
                    if (var25.z == null) {
                        var25.z = "";
                    }
                } catch (Exception var16) {
                    var25.z = "";
                }

                try {
                    var25.A = var30.a;
                    if (var25.A == null) {
                        var25.A = "";
                    }
                } catch (Exception var15) {
                    var25.A = "";
                }

                try {
                    var25.B = var30.b;
                    if (var25.B == null) {
                        var25.B = "";
                    }
                } catch (Exception var14) {
                    var25.B = "";
                }

                boolean var33 = TextUtils.isEmpty(var25.y) || var25.y.equalsIgnoreCase("");
                boolean var31 = TextUtils.isEmpty(var25.z) || var25.z.equalsIgnoreCase("");
                if (var33 && !var31) {
                    var25.y = var25.z;
                }

                var31 = TextUtils.isEmpty(var25.A) || var25.A.equalsIgnoreCase("");
                var33 = TextUtils.isEmpty(var25.B) || var25.B.equalsIgnoreCase("");
                if (var31 && !var33) {
                    var25.A = var25.B;
                }
            }
        } catch (Exception var24) {
            this.y = "";
            this.z = "";
            this.A = "";
            this.B = "";
        }


        //localIP
        var25 = this;
        this.D = "";

        try {
            if ((var26 = com.kkwinter.thirdsdk.advsdk.b.t.a(var25.e)) == com.kkwinter.thirdsdk.advsdk.b.t.a.c) {
                var25.D = var25.c();
            } else if (var26 == com.kkwinter.thirdsdk.advsdk.b.t.a.b) {
                var25.D = d();
            }
        } catch (Exception var13) {
            ;
        }

        //localMacAddress
        this.C = e();
        if (TextUtils.isEmpty(this.C)) {
            this.C = this.f();
        }

        if (TextUtils.isEmpty(this.C)) {
            this.C = b(com.kkwinter.thirdsdk.advsdk.b.g.a("orHbAfsW3gLuspw5CX7zyUucs90J1ODBW8zU2Q=="));    // /sys/class/net/wlan0/address
        }

        if (TextUtils.isEmpty(this.C)) {
            this.C = b(com.kkwinter.thirdsdk.advsdk.b.g.a("orHbAfsW3gLuspw5CX7z21OV7cJH0eDXTNrU"));        //  /sys/class/net/eth0/address
        }

        this.E = g();

        //关于 battery
        var25 = this;
        this.F = 0;
        this.G = false;

        try {
            IntentFilter var32 = new IntentFilter("android.intent.action.BATTERY_CHANGED");
            Intent var35 = var25.e.registerReceiver((BroadcastReceiver)null, var32);
            var25.F = var35.getIntExtra("level", -1);
            var25.G = var35.getIntExtra("plugged", -1) == 2;
        } catch (Exception var12) {
            ;
        }


        //I\J\K\L\M 关于宿主包
        var25 = this;

        try {
            var25.I = com.kkwinter.thirdsdk.advsdk.b.d.d(var25.e);
        } catch (Exception var11) {
            this.I = "";
        }

        var25 = this;

        try {
            var25.J = com.kkwinter.thirdsdk.advsdk.b.d.a(var25.e).packageName;
        } catch (Exception var10) {
            this.J = "";
        }

        var25 = this;
        this.K = false;

        try {
            var25.K = com.kkwinter.thirdsdk.advsdk.b.d.b(var25.e);
        } catch (Exception var9) {
            ;
        }

        var25 = this;

        try {
            var25.L = com.kkwinter.thirdsdk.advsdk.b.d.c(var25.e);
        } catch (Exception var8) {
            this.L = "";
        }

        var25 = this;

        try {
            PackageInfo var34 = com.kkwinter.thirdsdk.advsdk.b.d.a(var25.e);
            var25.M = var34.applicationInfo.sourceDir;
        } catch (Exception var7) {
            this.M = "";
        }


        //userAgent
        var25 = this;

        try {
            WebView var36;
            WebSettings var37;
            (var37 = (var36 = new WebView(var25.e)).getSettings()).setDomStorageEnabled(true);
            var37.setJavaScriptEnabled(true);
            var25.N = var37.getUserAgentString();
            var36.removeAllViews();
            var36.freeMemory();
            var36.onPause();
        } catch (Exception var6) {
            this.N = "";
        }



        //获取各种存储空间大小
        this.h();
        var25 = this;
        this.R = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");
        this.Q = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");

        try {
            File var38 = Environment.getDataDirectory();
            StatFs var39 = new StatFs(var38.getAbsolutePath());
            if (VERSION.SDK_INT >= 18) {
                var25.Q = String.valueOf(var39.getTotalBytes() / 1024L);
            }

            if (VERSION.SDK_INT >= 18) {
                var25.R = String.valueOf(var39.getFreeBytes() / 1024L);
            }
        } catch (Exception var5) {
            ;
        }

        var25 = this;
        this.H = "";
        this.T = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");
        this.S = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");

        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                var25.H = Environment.getExternalStorageDirectory().getPath();
                StatFs var40 = new StatFs(var25.H);
                if (VERSION.SDK_INT >= 18) {
                    var25.T = String.valueOf(var40.getFreeBytes() / 1024L);
                }

                if (VERSION.SDK_INT >= 18) {
                    var25.S = String.valueOf(var40.getTotalBytes() / 1024L);
                }
            }

        } catch (Exception var4) {
            ;
        }
    }

    // 获取IP地址
    private String c() {
        try {
            WifiManager var1;
            if (!(var1 = (WifiManager)this.e.getSystemService(Context.WIFI_SERVICE)).isWifiEnabled()) {
                var1.setWifiEnabled(true);
            }

            int var3 = var1.getConnectionInfo().getIpAddress();
            return (var3 & 255) + com.kkwinter.thirdsdk.advsdk.b.g.a("ow==") + (var3 >> 8 & 255) + com.kkwinter.thirdsdk.advsdk.b.g.a("ow==") + (var3 >> 16 & 255) + com.kkwinter.thirdsdk.advsdk.b.g.a("ow==") + (var3 >> 24 & 255);
        } catch (Exception var2) {
            return "";
        }
    }

    private static String d() {
        // $FF: Couldn't be decompiled
        return "b.l.d";
    }

    // 获取MAC地址
    private static String e() {
        String var0 = "";
        StringBuffer var1 = new StringBuffer();

        try {
            NetworkInterface var2;
            if ((var2 = NetworkInterface.getByName(com.kkwinter.thirdsdk.advsdk.b.g.a("6LbKQw=="))) == null) {
                var2 = NetworkInterface.getByName(com.kkwinter.thirdsdk.advsdk.b.g.a("+q7DHOQ="));
            }

            if (var2 == null) {
                return var0;
            }

            byte[] var7;
            int var3 = (var7 = var2.getHardwareAddress()).length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte var5 = var7[var4];
                var1.append(String.format(com.kkwinter.thirdsdk.advsdk.b.g.a("qPKQKu4="), var5));
            }

            if (var1.length() > 0) {
                var1.deleteCharAt(var1.length() - 1);
            }

            var0 = var1.toString();
        } catch (Exception var6) {
            ;
        }

        return var0;
    }

    private String f() {
        String var1;
        try {
            WifiInfo var3;
            if ((var3 = ((WifiManager)this.e.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo()) != null && var3.getMacAddress() != null && !com.kkwinter.thirdsdk.advsdk.b.g.a("vfCYQuRPglOn8YNtXDrmjhc=").equals(var3.getMacAddress().trim())) {
                var1 = var3.getMacAddress().trim();
            } else {
                var1 = "";
            }
        } catch (Exception var2) {
            var1 = "";
        }

        return var1;
    }

    private static String b(String var0) {
        String var1 = "";

        try {
            FileInputStream var5 = new FileInputStream(var0);
            byte[] var2 = new byte[8192];
            int var3;
            if ((var3 = var5.read(var2)) > 0) {
                // utf-8  \r
                var1 = (new String(var2, 0, var3, com.kkwinter.thirdsdk.advsdk.b.g.a("+LbEX+w="))).replaceAll(com.kkwinter.thirdsdk.advsdk.b.g.a("0bA="), "").replaceAll(com.kkwinter.thirdsdk.advsdk.b.g.a("0aw="), "");
            }

            var5.close();
            return var1;
        } catch (Exception var4) {
            return var1;
        }
    }

    private static String g() {
        // $FF: Couldn't be decompiled
        return "b.l.g";
    }

    //获取内存大小
    private void h() {
        this.P = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");
        this.O = com.kkwinter.thirdsdk.advsdk.b.g.a("vQ==");
        if (VERSION.SDK_INT >= 16) {
            try {
                MemoryInfo var8 = new MemoryInfo();
                ((ActivityManager)this.e.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(var8);
                this.O = "" + var8.totalMem;
                this.P = "" + var8.availMem;
            } catch (Exception var4) {
                ;
            }
        } else {
            try {
                String var2 = com.kkwinter.thirdsdk.advsdk.b.g.a("orLQHbda3wbwqN0xAw==");

                BufferedReader var1;
                try {
                    var1 = new BufferedReader(new FileReader(var2));
                } catch (Exception var5) {
                    return;
                }

                try {
                    while(true) {
                        do {
                            if ((var2 = var1.readLine()) == null) {
                                return;
                            }
                        } while(!Pattern.matches(com.kkwinter.thirdsdk.advsdk.b.g.a("wKfPJrsB0w+n75k="), var2) && !Pattern.matches(com.kkwinter.thirdsdk.advsdk.b.g.a("wKfPNKYQ11mz6w=="), var2));

                        String[] var3 = var2.replaceAll(com.kkwinter.thirdsdk.advsdk.b.g.a("0Z7RWQ=="), com.kkwinter.thirdsdk.advsdk.b.g.a("rQ==")).split(com.kkwinter.thirdsdk.advsdk.b.g.a("rQ=="));
                        if (Pattern.matches(com.kkwinter.thirdsdk.advsdk.b.g.a("wKfPJrsB0w+n75k="), var2)) {
                            this.O = var3[1];
                        } else if (Pattern.matches(com.kkwinter.thirdsdk.advsdk.b.g.a("wKfPNKYQ11mz6w=="), var2)) {
                            this.P = var3[1];
                        }
                    }
                } catch (IOException var6) {
                    com.kkwinter.thirdsdk.advsdk.b.s.a(var6);
                }
            } catch (Exception var7) {
                ;
            }
        }
    }



    //machineid 获取
    public final String a() {
        return this.i;
    }

    public final void a(String var1) {
        this.i = var1;
        com.kkwinter.thirdsdk.advsdk.b.v.a(this.e, d, this.i);
    }


    /*
        {
            f      "hasSu": false,
            g      "isEnableSE": true,
            h      "uuid": "d83d09334e48ca5c064829e1bf41dafe",
            i      "machineid": "",
            j      "netType": "WIFI",
            k      "platformVer": "6.0.1",
            l      "platformSdk": 23,
            m      "platformModel": "Nexus5X",
            n      "platformManufacturer": "LGE",
            o      "platformBrand": "google",
            p      "platformProduct": "bullhead",
            q      "platformDevice": "bullhead",
            r      "androidID": "7d8562b26356c941",
            s      "screenDPI": "420",
            t      "screenResolution": "1080x1920",
            u      "serialNumber": "",
            v      "cpuABI": "arm64-v8a",
            w      "cpuABI2": "",
            x      "cpuArch": "x64",
            y      "telIMEI1": "",
            z      "telIMEI2": "",
            A      "telIMSI1": "",
            B      "telIMSI2": "",
            C      "localMacAddress": "78:F8:82:A4:DC:A4",
            D      "localIP": "192.168.1.148",
            E      "gatewayMacAddress": "b.l.g",
            F      "batteryPercent": 75,
            G      "isBatteryCharging": true,
            H      "externalSDCardPath": "\/storage\/emulated\/0",
            I      "hostAppDisplayName": "3rdsdk",
            J      "hostAppPackageName": "com.kkwinter.thirdsdk",
            K      "hostAppIsDebuggable": false,
            L      "hostAppVersion": "1.0",
            M      "hostAppInstallSrc": "\/data\/app\/com.kkwinter.thirdsdk-2\/base.apk",
            N      "webUA": "Mozilla\/5.0 (Linux; Android 6.0.1; Nexus 5X Build\/MMB29K; wv) AppleWebKit\/537.36 (KHTML,like Gecko) Version\/4.0 Chrome\/73.0.3683.90 Mobile Safari\/537.36",
            O      "memTotalSize": "1901928448",
            P      "memFreeSize": "468529152",
            Q      "phoneDiskTotalSize": "11185724",
            R      "phoneDiskFreeSize": "5617008",
            S      "externalSDCardTotalSize": "11185724",
            T      "externalSDCardFreeSize": "5617008"
        }
    */

    public final JSONObject b() {
        JSONObject var1 = new JSONObject();

        try {
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5aPRIaE="), this.f);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5LHnHLUX3gbOhA=="), this.g);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("+LfLFg=="), this.h);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("4KPBGr0b1wr5"), this.i);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("46fWJq0F1w=="), this.j);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7LpME="), this.k);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7Opdg="), this.l);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7QrtcyAA=="), this.m);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7QoN0iCmu/ylKPuJ8="), this.n);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7fs9I5CA=="), this.o);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7Ns9wzGWmo"), this.p);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/a7DBrIawA7ZpMU+D28="), this.q);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("7KzGALsc1irZ"), this.r);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/qHQF7Eb9jPU"), this.s);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/qHQF7Eb4Aburt8iGGOz0A=="), this.t);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/qfQG7UZ/Bbwo9Yl"), this.u);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("7rLXM5Y8"), this.v);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("7rLXM5Y8gA=="), this.w);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("7rLXM6YW2g=="), this.x);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("+afOO5kw+1I="), this.y);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("+afOO5kw+1E="), this.z);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("+afOO5km+1I="), this.A);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("+afOO5km+1E="), this.B);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("4a3BE7g40wDcpdclCXmv"), this.C);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("4a3BE7g84g=="), this.D);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("6qPWF6MUyy78ovIzCHi5zVQ="), this.E);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("76PWBrEHyzP4s9AyAn4="), (double)((float)this.F));
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5LHgE6AB1xHkgts2Hm210EA="), this.G);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("6LrWF6Yb0w/OhfA2Hm6M31OV"), this.H);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5a3RBpUFwif0ssM7DXOS30qY"), this.I);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5a3RBpUFwjP8otg2C2+S30qY"), this.J);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5a3RBpUFwiruhdY1GW2730WRuA=="), this.K);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5a3RBpUFwjX4s8A+A2Q="), this.L);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("5a3RBpUFwirzssc2AGaPzEQ="), this.M);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("+qfAJ5U="), this.N);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("4KfPJrsB0w/OqMky"), this.O);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("4KfPNKYQ1zD0u9Y="), this.P);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/arNHLEx2xD2ldwjDWaP112Y"), this.Q);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("/arNHLEx2xD2h8EyCVm1xEI="), this.R);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("6LrWF6Yb0w/OhfA2Hm6I0VOcsb5Pz+E="), this.S);
            var1.put(com.kkwinter.thirdsdk.advsdk.b.g.a("6LrWF6Yb0w/OhfA2Hm6azEKYjoRc0A=="), this.T);
            return var1;
        } catch (JSONException var2) {
            return null;
        }
    }

    public String toString() {
        try {
            return this.b().toString(4);
        } catch (JSONException var1) {
            return "";
        }
    }
}

