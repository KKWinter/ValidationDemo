package com.kkwinter.thirdsdk.advworkflow.b;

import android.content.Context;
import android.webkit.WebView;

import java.util.HashMap;

import org.json.JSONObject;

public final class n extends a {
    private WebView k;

    public n(Context var1, String var2, String var3, String var4, String var5, long var6, JSONObject var8, String var9, HashMap<String, Object> var10, aa var11) {
        super(var1, var2, var3, var4, var5, var6, var8, var9, var10, var11);
    }

    public final void a() {
        c var1 = new c();
        StringBuilder var2 = new StringBuilder();
        this.k = com.kkwinter.thirdsdk.advworkflow.b.a.a(this.a, var1, var2);

        if (this.k == null) {
            this.b(0, com.kkwinter.thirdsdk.advworkflow.b.e.a("MmU2wG/vfxW0SGT/Wf9EzvH6zL36") + var2.toString() + com.kkwinter.thirdsdk.advworkflow.b.e.a("/g=="));
        } else {
            String var3 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.i, com.kkwinter.thirdsdk.advworkflow.b.e.a("soPZV60AWjw="), com.kkwinter.thirdsdk.advworkflow.b.e.a("v5nZVe56Jw=="));
            this.k.loadUrl(var3);
        }
    }
}

