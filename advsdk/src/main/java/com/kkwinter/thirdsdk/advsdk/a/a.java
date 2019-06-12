package com.kkwinter.thirdsdk.advsdk.a;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.InflaterInputStream;

final class a implements g {
    a() {
    }

    public final String a(InputStream var1) throws IOException {
        if (var1 == null) {
            return null;
        } else {
            StringBuilder var2 = new StringBuilder();

            try {
                InflaterInputStream var3 = new InflaterInputStream(var1);
                BufferedReader var7 = new BufferedReader(new InputStreamReader(var3, "UTF-8"));

                String var4;
                while((var4 = var7.readLine()) != null) {
                    var2.append(var4).append("\n");
                }
            } finally {
                var1.close();
            }

            return var2.toString();
        }
    }
}
