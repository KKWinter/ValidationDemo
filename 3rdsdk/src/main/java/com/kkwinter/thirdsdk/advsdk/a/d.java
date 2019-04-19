package com.kkwinter.thirdsdk.advsdk.a;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class d {
    private final Map<String, String> a = new HashMap();
    private final List<HttpCookie> b = new ArrayList();
    private int c;
    private int d;
    private String e;
    private boolean f;

    d() {
    }

    public final Map<String, String> a() {
        return this.a;
    }

    public final void a(String var1, String var2) {
        this.a.put(var1, var2);
    }

    public final List<HttpCookie> b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final void d() {
        this.c = 60000;
    }

    public final int e() {
        return this.d;
    }

    public final void f() {
        this.d = 60000;
    }

    public final String g() {
        return this.e;
    }

    public final void a(String var1) {
        this.e = var1;
    }

    public final boolean h() {
        return this.f;
    }

    public final void i() {
        this.f = true;
    }
}
