package com.kkwinter.thirdsdk.advworkflow.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class j implements h {

    public final String a(InputStream paramInputStream) throws IOException {

        if (paramInputStream == null) {
            return null;
        }
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
        StringBuilder localStringBuilder = new StringBuilder();
        try {
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
