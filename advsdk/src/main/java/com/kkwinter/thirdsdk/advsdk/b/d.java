package com.kkwinter.thirdsdk.advsdk.b;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

/**
 * 获取宿主包信息工具类
 */
public final class d {
    private static final X500Principal a = new X500Principal("CN=Android Debug,O=Android,C=US");

    public static PackageInfo a(Context var0) {
        PackageManager var1 = var0.getPackageManager();
        PackageInfo var2 = null;

        try {
            var2 = var1.getPackageInfo(var0.getPackageName(), 0);
        } catch (NameNotFoundException var3) {
            var3.printStackTrace();
        }

        return var2;
    }

    public static boolean b(Context var0) {
        boolean var1 = false;

        try {
            Signature[] var7 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 64).signatures;

            for(int var2 = 0; var2 < var7.length; ++var2) {
                CertificateFactory var3 = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream var4 = new ByteArrayInputStream(var7[var2].toByteArray());
                if (var1 = ((X509Certificate)var3.generateCertificate(var4)).getSubjectX500Principal().equals(a)) {
                    break;
                }
            }
        } catch (NameNotFoundException var5) {
            ;
        } catch (CertificateException var6) {
            ;
        }

        return var1;
    }

    public static String c(Context var0) {
        PackageInfo var1;
        return (var1 = a(var0)) == null ? "" : var1.versionName;
    }

    public static String d(Context var0) {
        PackageInfo var1;
        return (var1 = a(var0)) == null ? "" : var1.applicationInfo.loadLabel(var0.getPackageManager()).toString();
    }
}

