package com.example.testh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "tmysdk";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    // /data/user/0/com.kkwinter.applink/app_tmy_upt
    private static final String TMY_SDK_DIR = "tmy_upt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context  = this.getApplicationContext();
        checkPermission(context);

        findViewById(R.id.test_start).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean ok = com.android.tmy.sdk.util.SDKUtils.create(getApplicationContext(), TMY_SDK_DIR, null);   //"tmy.dat");

//                boolean ok = com.example.testh.tmysdk.SDKUtils.create(getApplication(), TMY_SDK_DIR, "tmy.dat");
//                boolean ok = android.support.vx.net.TrafficUtils.init(context, null, "BX_W1014", TMY_SDK_DIR);

                if (ok) {
                    Toast.makeText(getApplicationContext(), "create ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "create failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        findViewById(R.id.test_update).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                File dstfile = downloadUpdateFile(getApplicationContext());
                if (dstfile != null && dstfile.exists()) {
                    //更新成功不会立刻生效， 会在进程重启后调用create成功后生效。
                    boolean ok = com.android.tmy.sdk.util.SDKUtils.update(getApplicationContext(), dstfile, TMY_SDK_DIR);

                    if (ok) {
                        Toast.makeText(getApplicationContext(), "update ok", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.test_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean ok = com.android.tmy.sdk.util.SDKUtils.destroy();
                if (ok) {
                    Toast.makeText(getApplicationContext(), "close ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "close failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 下载 SDK的更新文件到本地
     * <p>
     * 本例子使用raw文件代替下载文件过程。 实际使用时应是从服务器下载文件的过程。
     *
     * @param context
     * @return 下载好的文件
     */
    private File downloadUpdateFile(Context context) {
        InputStream is = MainActivity.this.getResources().openRawResource(R.raw.upt);
        if (is != null) {
            File tmpdir = getApplicationContext().getDir("tmp", 0);  //  /data/user/0/com.kkwinter.applink/app_tmp
            String dstfilepatch = tmpdir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".tmp";
            File dstfile = new File(dstfilepatch);
            if (copyFile(getApplicationContext(), R.raw.upt, dstfile)) {
                return dstfile;
            }
        }

        return null;
    }


    private static boolean copyFile(Context context, int rawFileId, File dstFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dstFile.exists()) {
                dstFile.delete();
            }
            dstFile.createNewFile();
            out = new FileOutputStream(dstFile);
            byte[] buffer = new byte[4096];
            in = context.getResources().openRawResource(rawFileId);
            while (true) {
                int count = in.read(buffer);
                if (count <= 0) {
                    break;
                }
                out.write(buffer, 0, count);
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
                in = null;
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
                out = null;
            }
        }
        return true;
    }


    private void checkPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permissions, 111);
                }
            }
        }
    }
}
