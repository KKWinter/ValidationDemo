package com.jumpraw.launcher;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;


/**
 * 静默安装、卸载，需要系统权限
 */
public class MainActivity extends Activity {

    private Context context;
    private static final String TAG = "launcher";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String path = Environment.getExternalStorageDirectory() + "/nosense.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        checkPermission();

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                installApp(path);
            }
        });


        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                uninstallApp();

                delete(context, "com.jumpraw.plugin");
            }
        });

    }


    //静默安装
    public boolean installApp(String apkPath) {
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                process = new ProcessBuilder("pm", "install", "-i", "com.example.ddd", "-r", apkPath).start();
            } else {
                process = new ProcessBuilder("pm", "install", "-r", apkPath).start();
            }

            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {

            }
            if (process != null) {
                process.destroy();
            }
        }
        Log.e("result", "" + errorMsg.toString());
        Toast.makeText(context, errorMsg.toString() + "  " + successMsg, Toast.LENGTH_LONG).show();
        //如果含有“success”单词则认为安装成功
        return successMsg.toString().equalsIgnoreCase("success");
    }


    //静默卸载
    public void uninstallApp() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent sender = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        PackageInstaller mPackageInstaller = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mPackageInstaller = MainActivity.this.getPackageManager().getPackageInstaller();
            mPackageInstaller.uninstall("com.jumpraw.plugin", sender.getIntentSender());// 卸载APK
        }
    }


    //弹窗卸载界面
    public static void delete(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void loadPlugin() {

        Intent intent = new Intent();
        intent.setPackage("com.jumpraw.plugin");
//        intent.setAction("com.jumpraw.plugin.kkwinter");

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> plugins = pm.queryIntentActivities(intent, 0);
        ResolveInfo resolveInfo = plugins.get(0);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        String div = System.getProperty("path.separator");
        String packageName = activityInfo.packageName;
        String dexPath = activityInfo.applicationInfo.sourceDir;
        String dexOutputDir = context.getApplicationInfo().dataDir;
        String dir = context.getDir("case", 0).getAbsolutePath();
        String libPath = activityInfo.applicationInfo.nativeLibraryDir;


        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, dir, libPath, context.getClassLoader());

        try {
            Class cls = dexClassLoader.loadClass(packageName + ".ToastUtils");
            Method method = cls.getDeclaredMethod("getPackageName", Context.class, String.class);
            method.setAccessible(true);
            method.invoke(null, context, "launcher");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadApk(String path) {
        String dir = context.getDir("case", 0).getAbsolutePath();
        DexClassLoader dexClassLoader = new DexClassLoader(path, dir, null, context.getClassLoader());

        try {
            Class cls = dexClassLoader.loadClass("com.jumpraw.plugin.ToastUtils");
            Method method = cls.getDeclaredMethod("getPackageName", Context.class, String.class);
            method.setAccessible(true);
            method.invoke(null, context, "launcher2");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, 321);
                }
            }

        }
    }
}


