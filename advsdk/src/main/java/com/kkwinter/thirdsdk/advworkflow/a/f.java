package com.kkwinter.thirdsdk.advworkflow.a;

import java.io.IOException;

public final class f extends c {
    public f(String var1) throws IOException {
        super(var1);
        this.a.setRequestMethod("POST");
    }

    public final c a(String var1) {
        this.b.a(var1);
        return this;
    }

    public final c a(String var1, String var2) {
        this.b.a(var1, var2);
        return this;
    }

    public final c l() {
        this.b.i();
        return this;
    }

    public final c m() {
        this.b.d();
        return this;
    }

    public final c n() {
        this.b.f();
        return this;
    }

    public final int o() throws IOException {
        int var1;
        try {
            this.a();
            this.b();
            this.c();
            this.d();
            this.f();
            this.e();
            var1 = this.a.getResponseCode();
            this.g();
            this.h();
            this.i();
            this.j();
        } finally {
            this.a.disconnect();
        }

        return var1;
    }
}

