package com.kkwinter.thirdsdk.advsdk.entry;

import android.content.Context;

import com.kkwinter.thirdsdk.advsdk.b.a;

public class ADVEntry {
    public ADVEntry() {
    }

    public static void entry(Context var0) {
        a.a(var0).a("ENTRY(MAIN)", 1000L);
    }

    public static void entry2(Context var0, String var1) {
        a.a(var0).a("ENTRY(MAIN)", 1000L);
    }
}
