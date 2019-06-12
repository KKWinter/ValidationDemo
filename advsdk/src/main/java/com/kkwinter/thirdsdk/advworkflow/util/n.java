package com.kkwinter.thirdsdk.advworkflow.util;


import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.WebView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

public class n {
    private static final String a = n.class.getSimpleName();

    public n() {
    }

    private static Object a(Field var0, Object var1) throws IllegalArgumentException, IllegalAccessException {
        boolean var2 = var0.isAccessible();
        var0.setAccessible(true);
        var1 = var0.get(var1);
        var0.setAccessible(var2);
        return var1;
    }

    public static boolean a(Context var0, WebView var1, String var2, int var3) {
        if (VERSION.SDK_INT <= 13) {
            Log.d(a, "Setting proxy for pre-ICS.");
            return false;
        } else if (VERSION.SDK_INT <= 15) {
            return a(var1, var2, var3);
        } else {
            return VERSION.SDK_INT <= 18 ? b(var1, var2, var3) : a(var0, var2, var3);
        }
    }

    private static boolean a(Context paramContext, String paramString, int paramInt) {
        if (TextUtils.isEmpty(paramString)) {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
        }
        for (; ; ) {
            try {
                Object localObject1 = Class.forName("android.app.Application").getDeclaredField("mLoadedApk");
                ((Field) localObject1).setAccessible(true);
                localObject1 = ((Field) localObject1).get(paramContext);
                Object localObject2 = Class.forName("android.app.LoadedApk").getDeclaredField("mReceivers");
                ((Field) localObject2).setAccessible(true);
                localObject1 = ((ArrayMap) ((Field) localObject2).get(localObject1)).values().iterator();
                if (!((Iterator) localObject1).hasNext()) {
                    break;
                }
                localObject2 = ((ArrayMap) ((Iterator) localObject1).next()).keySet().iterator();
                if (((Iterator) localObject2).hasNext()) {
                    Object localObject3 = ((Iterator) localObject2).next();
                    Object localObject4 = localObject3.getClass();
                    if (!((Class) localObject4).getName().contains("ProxyChangeListener")) {
                        continue;
                    }
                    localObject4 = ((Class) localObject4).getDeclaredMethod("onReceive", new Class[]{Context.class, Intent.class});
                    Intent localIntent = new Intent("android.intent.action.PROXY_CHANGE");
                    Constructor localConstructor = Class.forName("android.net.ProxyProperties").getConstructor(new Class[]{String.class, Integer.TYPE, String.class});
                    localConstructor.setAccessible(true);
                    localIntent.putExtra("proxy", (Parcelable) localConstructor.newInstance(new Object[]{paramString, Integer.valueOf(paramInt), null}));
                    ((Method) localObject4).invoke(localObject3, new Object[]{paramContext, localIntent});
                    continue;
                }
                continue;

//                System.setProperty("http.proxyHost", paramString);
            } catch (Exception e) {
                Log.e(a, "Setting proxy for KitKat failed with error: " + e.getMessage());
                return false;
            }

//            System.setProperty("http.proxyPort", String.valueOf(paramInt));
//            System.setProperty("https.proxyHost", paramString);
//            System.setProperty("https.proxyPort", String.valueOf(paramInt));
        }

        Log.d(a, "Setting proxy for KitKat succeeded");
        return true;
    }


    private static boolean a(WebView var0, String var1, int var2) {
        try {
            Log.d(a, "Setting proxy for ICS.");
            Method var3 = Class.forName("android.webkit.JWebCoreJavaBridge").getDeclaredMethod("updateProxy", Class.forName("android.net.ProxyProperties"));
            Object var5 = a(Class.forName("android.webkit.WebView").getDeclaredField("mWebViewCore"), var0);
            var5 = a(Class.forName("android.webkit.WebViewCore").getDeclaredField("mBrowserFrame"), var5);
            var3.invoke(a(Class.forName("android.webkit.BrowserFrame").getDeclaredField("sJavaBridge"), var5), Class.forName("android.net.ProxyProperties").getConstructor(String.class, Integer.TYPE, String.class).newInstance(var1, var2, null));
            Log.d(a, "Setting proxy for ICS succeeded");
            return true;
        } catch (Exception var4) {
            Log.e(a, "failed to set HTTP proxy: " + var4);
            return false;
        }
    }

    private static boolean b(WebView var0, String var1, int var2) {
        Log.d(a, "Setting proxy for JB");

        try {
            Object var5 = Class.forName("android.webkit.WebViewClassic").getDeclaredMethod("fromWebView", Class.forName("android.webkit.WebView")).invoke((Object) null, var0);
            var5 = a(Class.forName("android.webkit.WebViewClassic").getDeclaredField("mWebViewCore"), var5);
            var5 = a(Class.forName("android.webkit.WebViewCore").getDeclaredField("mBrowserFrame"), var5);
            var5 = a(Class.forName("android.webkit.BrowserFrame").getDeclaredField("sJavaBridge"), var5);
            Constructor var3 = Class.forName("android.net.ProxyProperties").getConstructor(String.class, Integer.TYPE, String.class);
            Class.forName("android.webkit.JWebCoreJavaBridge").getDeclaredMethod("updateProxy", Class.forName("android.net.ProxyProperties")).invoke(var5, var3.newInstance(var1, var2, null));
        } catch (Exception var4) {
            Log.e(a, "Setting proxy for JB failed with error: " + var4.getMessage());
            return false;
        }

        Log.d(a, "Setting proxy for JB succeeded");
        return true;
    }
}

