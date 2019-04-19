package com.kkwinter.thirdsdk.advworkflow.util;

import android.content.Context;
import android.os.Build.VERSION;
import javax.security.auth.x500.X500Principal;

public final class a {
    private static final X500Principal a = new X500Principal("CN=Android Debug,O=Android,C=US");

    public static boolean a(String var0, Context var1) {
        return VERSION.SDK_INT < 23 || var1.checkSelfPermission(var0) == 0;
    }
}
