package com.kkwinter.thirdsdk.advworkflow.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

final class b implements h {
    public final String a(InputStream paramInputStream)
            throws IOException {
        if (paramInputStream == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        try {
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(paramInputStream), "UTF-8"));
            for (; ; ) {
                String str = localBufferedReader.readLine();
                if (str == null) {
                    break;
                }
                localStringBuilder.append(str).append("\n");
            }
        } finally {
            paramInputStream.close();
        }
        return localStringBuilder.toString();
    }
}
