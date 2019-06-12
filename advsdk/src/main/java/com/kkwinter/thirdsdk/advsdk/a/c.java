package com.kkwinter.thirdsdk.advsdk.a;



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

//网络请求类
public abstract class c {
    protected final HttpURLConnection a;
    protected final d b = new d();
    private final f c = new f();

    protected c(String var1) throws IOException {
        URL var2 = new URL(var1);
        this.a = (HttpURLConnection)var2.openConnection();
    }


    //设置请求头参数
    protected final void a() {
        if (this.b.a() != null) {
            Iterator var1 = this.b.a().entrySet().iterator();

            while(var1.hasNext()) {
                Entry var2 = (Entry)var1.next();
                this.a.setRequestProperty((String)var2.getKey(), (String)var2.getValue());
            }
        }

    }


    //设置请求头中的cookie
    protected final void b() {
        if (this.b.b() != null) {
            StringBuilder var1 = new StringBuilder();
            Iterator var2 = this.b.b().iterator();

            while(var2.hasNext()) {
                HttpCookie var3 = (HttpCookie)var2.next();
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


    //设置post请求 body
    protected final void e() throws IOException {
        if (this.b.g() != null) {
            this.a.setDoOutput(true);
            DataOutputStream var1;
            (var1 = new DataOutputStream(this.a.getOutputStream())).writeBytes(this.b.g());  // post body
            var1.flush();
            var1.close();
        }

    }

    //设置本次连接是否自动处理重定向
    protected final void f() {
        if (!this.b.h()) {
            this.a.setInstanceFollowRedirects(this.b.h());
        }

    }

    protected final void g() {
        Map var1;
        if ((var1 = this.a.getHeaderFields()) != null) {
            this.c.a().putAll(var1);
        }

    }

    protected final void h() {
        List var1;
        if ((var1 = (List)this.a.getHeaderFields().get("Set-Cookie")) != null) {
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
                String var2 = (String)var3.next();
                this.c.b().add(HttpCookie.parse(var2).get(0));
            }
        }

    }

    protected final void i() {
        List var1;
        if ((var1 = (List)this.a.getHeaderFields().get("Location")) != null) {
            this.c.a((String)var1.get(0));
        }

    }

    protected final void j() throws IOException {
        g var1;
        if ((var1 = h.a(this.a.getContentEncoding())) == null) {
            throw new IOException("Failed to get stream converter");
        } else {
            InputStream var2;
            try {
                if ((var2 = this.a.getInputStream()) != null) {
                    this.c.b(var1.a(var2));
                }

            } catch (IOException var4) {
                try {
                    if ((var2 = this.a.getErrorStream()) != null) {
                        this.c.b(var1.a(var2));
                    }

                } catch (IOException var3) {
                    throw var3;
                }
            }
        }
    }

    public final String k() {
        return this.c.c();
    }

    public abstract c l();
}

