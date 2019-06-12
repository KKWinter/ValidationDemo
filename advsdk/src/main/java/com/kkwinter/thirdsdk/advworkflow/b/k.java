package com.kkwinter.thirdsdk.advworkflow.b;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.HashMap;
import org.json.JSONObject;

public final class k extends a {
    private static WebView k = null;
    private Runnable l = null;

    public k(Context var1, String var2, String var3, String var4, String var5, long var6, JSONObject var8, String var9, HashMap<String, Object> var10, aa var11) {
        super(var1, var2, var3, var4, var5, var6, var8, var9, var10, var11);
    }

    protected static void a(WebView var0) {
        if (var0 != null) {
            try {
                var0.removeAllViews();
                var0.freeMemory();
                var0.onPause();
                var0.destroy();
            } catch (Exception var1) {
                ;
            }
        }
    }

    private String b() {
        try {
            String var1 = com.kkwinter.thirdsdk.advworkflow.util.k.a(com.kkwinter.thirdsdk.advworkflow.util.k.a(this.i, "task", (JSONObject)null), "url", "");
            return var1;
        } catch (Exception var2) {
            return "";
        }
    }

    public final void a() {
        StringBuilder var1 = new StringBuilder();
        WebView var2 = a(this.a, new c(), var1);
        k = var2;
        if (var2 == null) {
            this.a(0, "创建webView失败 (" + var1.toString() + ")");
        } else {
            k.setWebViewClient(new WebViewClient() {
                public final void onPageFinished(final WebView var1, String var2) {
                    if (l != null) {
                        com.kkwinter.thirdsdk.advworkflow.util.j.b(l);
                        k.this.l = null;
                    }

                    l = new Runnable() {
                        public final void run() {
                            k.this.a(1, "");
                            com.kkwinter.thirdsdk.advworkflow.b.k.a(var1);
                        }
                    };
                    com.kkwinter.thirdsdk.advworkflow.util.j.a(k.this.l, 120000L);
                }
            });
            String var3 = this.b();
            k.loadUrl(var3);
        }
    }
}

