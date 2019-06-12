package com.kkwinter.thirdsdk.advworkflow.a;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class g {
    private final Map<String, List<String>> a = new HashMap();
    private final List<HttpCookie> b = new ArrayList();
    private String c = "";
    private String d = "";

    g() {
    }

    public final Map<String, List<String>> a() {
        return this.a;
    }

    public final void a(String var1) {
        this.c = var1;
    }

    public final List<HttpCookie> b() {
        return this.b;
    }

    public final void b(String var1) {
        this.d = var1;
    }

    public final String c() {
        return this.d;
    }
}
