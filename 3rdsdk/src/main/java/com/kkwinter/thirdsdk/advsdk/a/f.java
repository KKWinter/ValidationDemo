package com.kkwinter.thirdsdk.advsdk.a;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class f {
    private final Map<String, List<String>> a = new HashMap();
    private final List<HttpCookie> b = new ArrayList();
    private String c = "";
    private String d = "";

    f() {
    }

    public final Map<String, List<String>> a() {
        return this.a;
    }

    public final List<HttpCookie> b() {
        return this.b;
    }

    public final void a(String var1) {
        this.c = var1;
    }

    public final String c() {
        return this.d;
    }

    public final void b(String var1) {
        this.d = var1;
    }
}
