package com.kkwinter.applink;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class In {
  static String a = "ARCFOUR";
  static String b = "n4BLeGUQmdhAeAIf";
  
  public static String a(String paramString)
  {
    Object localObject = "sa/lBCf/rO3GtWPHS5/xoA==";
    try
    {
      Cipher localCipher = Cipher.getInstance(a);
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(b.getBytes(), a);
      localCipher.init(2, localSecretKeySpec);
      localObject = Base64.decode((String)localObject, 0);
      localObject = localCipher.doFinal((byte[])localObject);
      localObject = new SecretKeySpec((byte[])localObject, a);
      localCipher.init(2, (Key)localObject);

      byte[] bytes = Base64.decode(paramString, 0);
      bytes = localCipher.doFinal(bytes);
      paramString = new String(bytes);
    }
    catch (Exception localException)
    {
      paramString = "";
    }
    return paramString;
  }
}
