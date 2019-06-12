package com.kkwinter.thirdsdk.advsdk.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * 这是一个变化不同domain发请求的工具类
 */
public final class n {
    protected static com.kkwinter.thirdsdk.advsdk.b.n a = null;
    public static final String b = com.kkwinter.thirdsdk.advsdk.b.h.a("JYQ48E3dVOLY3gx8NXffOtdiQzXskg==");     // http://adv.jpigjqg.com
    public static final String c = com.kkwinter.thirdsdk.advsdk.b.h.a("JYQ48E3dVOLY3gwvfGfdI9R2QzXskg==");     // http://adv.99yesrs.com
    public static final String d = com.kkwinter.thirdsdk.advsdk.b.h.a("JYQ48E3dVOLY3gx7PG3dPMIrDjnu");         // http://adv.myseld.com
    public static final String e = com.kkwinter.thirdsdk.advsdk.b.h.a("JYQ48E3dVOLY3gx7JHLRPs9kA3jgkLc=");     // http://adv.malinian.com
    public static final String f = com.kkwinter.thirdsdk.advsdk.b.h.a("JYQ48E3dVOLY3gxnMH/WMtN3CDitnLVp");     // http://adv.quanburen.com
    public static final String g = com.kkwinter.thirdsdk.advsdk.b.h.a("JYQ48E3dVOLY3gxyIGrRMch3DDitnLVp");     // http://adv.detianran.com
    public static final String h = com.kkwinter.thirdsdk.advsdk.b.h.a("LJQ63wOTHNzd3kN/KX/aPMNBAjvilrQ=");                 // adv_tag_availableDomain
    public static final String i = com.kkwinter.thirdsdk.advsdk.b.h.a("LJQ63wOTHNzPzVBgIGz7P8hjBDHCk7ZrNPcUx9frgg==");     // adv_tag_serverConfigAllowDomain
    public static final String j = com.kkwinter.thirdsdk.advsdk.b.h.a("LJQ63wOTHNzPzVBgIGz7P8hjBDHRmqlwMdoY3vLtgb4Oow=="); // adv_tag_serverConfigRestrictDomain
    protected Context k;
    protected ArrayList<String> l;
    protected int m;   //要用的请求域名在l中的index

    //单例
    public static synchronized com.kkwinter.thirdsdk.advsdk.b.n a(Context var0) {
        return a != null ? a : (a = new com.kkwinter.thirdsdk.advsdk.b.n(var0));
    }

    //构造方法
    private n(Context var1) {
        this.k = var1;
        this.c(var1);
    }

    //准备请求域名列表，从中选一个做请求
    private void c(Context var1) {
        this.l = new ArrayList();
        if (!TextUtils.isEmpty("http://adv.jpigjqg.com")) {
            this.l.add("http://adv.jpigjqg.com");
        }

        this.l.add(b);
        this.l.add(c);
        this.l.add(d);
        this.l.add(e);
        this.l.add(f);
        this.l.add(g);

//        0 = "http://139.224.5.171"
//        1 = "http://adv.readingbooks.com.cn"
//        2 = "http://adv.patrickwong.com.cn"
//        3 = "http://adv.99yesrs.com"
        ArrayList var2 = c(com.kkwinter.thirdsdk.advsdk.b.v.b(this.k, i, ""));   //sp取出adv_tag_serverConfigAllowDomain  允许的域名

//        0 = "http://adv.quanburen.com"
//        1 = "http://adv.jpigjqg.com"
//        2 = "http://adv.detianran.com"
//        3 = "http://adv.malinian.com"
//        4 = "http://adv.myseld.com"
        ArrayList var3 = c(com.kkwinter.thirdsdk.advsdk.b.v.b(this.k, j, ""));   //sp取出adv_tag_serverConfigRestrictDomain   限制的域名

        int var4;
        for (var4 = 0; var4 < var2.size(); ++var4) {
            this.l.add((String) var2.get(var4));
        }

        for (var4 = 0; var4 < var3.size(); ++var4) {
            int var5;
            if ((var5 = a(this.l, (String) var3.get(var4))) >= 0) {
                this.l.remove(var5);
            }
        }

        //初始化完成，准备用index 为m 的url发请求【第一次为0，默认使用第一个】
        this.m = this.b(var1);
    }


    //从list中匹配到adv_tag_availableDomain字符串的index
    private int b(Context var1) {
        String var3;
        if (TextUtils.isEmpty(var3 = com.kkwinter.thirdsdk.advsdk.b.v.b(var1, h, ""))) {   //sp取出adv_tag_availableDomain
            return 0;
        } else {
            for (int var2 = 0; var2 < this.l.size(); ++var2) {
                if (((String) this.l.get(var2)).equalsIgnoreCase(var3)) {
                    return var2;
                }
            }

            return 0;
        }
    }

    //从list中匹配到的字符串的index
    private static int a(ArrayList<String> var0, String var1) {
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            if (((String) var0.get(var2)).equalsIgnoreCase(var1)) {
                return var2;
            }
        }

        return -1;
    }


    // 返回list l中index m的字符串，是请求域名
    public final String a() {
        return this.m >= 0 && this.m < this.l.size() ? (String) this.l.get(this.m) : "";
    }


    //第一次，把m位置的domain存储到adv_tag_availableDomain
    public final void b() {
        ++this.m;
        if (this.m < 0 || this.m >= this.l.size()) {
            this.m = 0;
        }

        String var1 = "";
        if (this.m < this.l.size()) {
            var1 = (String) this.l.get(this.m);
        }

        com.kkwinter.thirdsdk.advsdk.b.v.a(this.k, h, var1);   // 存储 adv_tag_availableDomain
    }



    //把list集合中的字符串，base64 encode，然后用@@连接，拼接成一个字符串
    private static String c(ArrayList<String> var0) {
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < var0.size(); ++var2) {
            var1.append(a((String) var0.get(var2)));
            if (var2 < var0.size() - 1) {
                var1.append(com.kkwinter.thirdsdk.advsdk.b.h.a("DbA="));
            }
        }

        return var1.toString();
    }


    //根据@@切割字符串，base64解密，并返回list集合
    private static ArrayList<String> c(String var0) {
        ArrayList var1 = new ArrayList();
        if (TextUtils.isEmpty(var0)) {
            return var1;
        } else {
            String[] var3 = var0.split(com.kkwinter.thirdsdk.advsdk.b.h.a("DbA="));

            for (int var2 = 0; var2 < var3.length; ++var2) {
                var1.add(b(var3[var2]));
            }

            return var1;
        }
    }


    //存储adv_tag_serverConfigAllowDomain
    public final void a(ArrayList<String> var1) {
        String var2 = c(var1);
        com.kkwinter.thirdsdk.advsdk.b.v.a(this.k, i, var2);
    }


    //存储adv_tag_serverConfigRestrictDomain
    public final void b(ArrayList<String> var1) {
        String var2 = c(var1);
        v.a(this.k, j, var2);
    }



    //base64 encode
    private static String a(String var0) {
        try {
            var0 = URLEncoder.encode(var0, com.kkwinter.thirdsdk.advsdk.b.h.a("OIQqrU8="));
        } catch (Exception var1) {
            ;
        }

        byte[] var2 = Base64.encode(var0.getBytes(), 0);
        return (new String(var2)).replaceAll(com.kkwinter.thirdsdk.advsdk.b.h.a("EZ4="), "").replaceAll(com.kkwinter.thirdsdk.advsdk.b.h.a("EYI="), "");
    }

    //base64 decode
    private static String b(String var0) {
        byte[] var2 = Base64.decode(var0, 0);
        var0 = new String(var2);

        try {
            var0 = URLDecoder.decode(var0, com.kkwinter.thirdsdk.advsdk.b.h.a("OIQqrU8="));
        } catch (Exception var1) {
            ;
        }

        return var0;
    }




}

