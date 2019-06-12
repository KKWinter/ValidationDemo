package com.kkwinter.thirdsdk.advworkflow.a;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class c {
    protected final HttpURLConnection a;
    protected final e b = new e();
    private final g c = new g();

    protected c(String var1) throws IOException {
        this.a = (HttpURLConnection) (new URL(var1)).openConnection();
    }

    protected final void a() {
        if (this.b.a() != null) {
            Iterator var1 = this.b.a().entrySet().iterator();

            while (var1.hasNext()) {
                Entry var2 = (Entry) var1.next();
                this.a.setRequestProperty((String) var2.getKey(), (String) var2.getValue());
            }
        }

    }

    protected final void b() {
        if (this.b.b() != null) {
            StringBuilder var1 = new StringBuilder();
            Iterator var2 = this.b.b().iterator();

            while (var2.hasNext()) {
                HttpCookie var3 = (HttpCookie) var2.next();
                var1.append(String.format("%s=%s;", var3.getName(), var3.getValue()));
            }

            String var4 = var1.toString().replaceAll(";$", "");
            this.a.setRequestProperty("Cookie", var4);
        }

    }

    protected final void c() {
        if (this.b.c() != 0) {
            this.a.setConnectTimeout(this.b.c());
        }

    }

    protected final void d() {
        if (this.b.e() != 0) {
            this.a.setReadTimeout(this.b.e());
        }

    }

    protected final void e() throws IOException {
        if (this.b.g() != null) {
            this.a.setDoOutput(true);
            DataOutputStream var1 = new DataOutputStream(this.a.getOutputStream());
            var1.writeBytes(this.b.g());
            var1.flush();
            var1.close();
        }

    }

    protected final void f() {
        if (!this.b.h()) {
            this.a.setInstanceFollowRedirects(this.b.h());
        }

    }

    protected final void g() {
        Map var1 = this.a.getHeaderFields();
        if (var1 != null) {
            this.c.a().putAll(var1);
        }

    }

    protected final void h() {
        List var1 = (List) this.a.getHeaderFields().get("Set-Cookie");
        if (var1 != null) {
            Iterator var3 = var1.iterator();

            while (var3.hasNext()) {
                String var2 = (String) var3.next();
                this.c.b().add(HttpCookie.parse(var2).get(0));
            }
        }

    }

    protected final void i() {
        List var1 = (List) this.a.getHeaderFields().get("Location");
        if (var1 != null) {
            this.c.a((String) var1.get(0));
        }

    }

    protected final void j() throws IOException {
        h localh = i.a(this.a.getContentEncoding());
        if (localh == null) {
            throw new IOException("Failed to get stream converter");
        }

        try {
            InputStream localInputStream1 = this.a.getInputStream();
            if (localInputStream1 != null) {
                this.c.b(localh.a(localInputStream1));
            }
            return;
        } catch (IOException localIOException2) {
            try {
                InputStream localInputStream2;
                do {
                    localInputStream2 = this.a.getErrorStream();
                } while (localInputStream2 == null);
                this.c.b(localh.a(localInputStream2));
                return;
            } catch (IOException localIOException1) {
                throw localIOException1;
            }
        }
    }

    public final String k() {
        return this.c.c();
    }

    public abstract c l();
}
