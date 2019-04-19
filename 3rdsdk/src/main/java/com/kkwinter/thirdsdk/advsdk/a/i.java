package com.kkwinter.thirdsdk.advsdk.a;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class i implements g {
    i() {
    }

    public final String a(InputStream var1) throws IOException {
        if (var1 == null) {
            return null;
        } else {
            BufferedReader var2 = new BufferedReader(new InputStreamReader(var1));
            StringBuilder var3 = new StringBuilder();

            String var4;
            try {
                while((var4 = var2.readLine()) != null) {
                    var3.append(var4).append("\n");
                }
            } finally {
                var1.close();
            }

            return var3.toString();
        }
    }
}