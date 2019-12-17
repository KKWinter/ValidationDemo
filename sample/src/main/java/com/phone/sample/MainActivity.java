package com.phone.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by huangdong on 18/10/9.
 * antony.huang@yeahmobi.com
 */
public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    private static String path = "/data/data/com.cloudtech.adsdk.example";
    private Context context;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private String url = "https://www.csdn.net";


    enum TestType {

        A_TYPE,
        B_TYPE,
        C_TYPE

    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        check();


        Button button = findViewById(R.id.bt);
        Button button2 = findViewById(R.id.bt2);
        Button button3 = findViewById(R.id.bt3);
//        final ListView lv = findViewById(R.id.lv);


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                TestType[] testTypes = TestType.values();

                for (TestType testType : testTypes) {

                    int index = testType.ordinal();
                    String value = testType.toString();

                    Log.i(TAG, "onClick: >>" + index + ",,,," + value);
                }


                String bo = "true";
                boolean result = Boolean.valueOf(bo);
                Log.i(TAG, "onClick: ==" + result);

//                String url = "http://track.mobgc.com/v1/click?offer_id=34&aff_id=10001&aid=e38186fc6a4a7a3d&gaid=b9a9f0e9-09d9-4f8b-b77b-71cde8b1ae98&aff_sub1=test";
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .get()//默认就是GET请求，可以不写
//                        .build();
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "onFailure: ");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                    }
//                });


//                CookieManager manager = CookieManager.getInstance();
//                String cookies = manager.getCookie(url);
//                Log.i(TAG, "onClick: >>>>>>>>" + cookies);

            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                CookieUtil.getCookie(MainActivity.this, url, new CookieUtil.OnCookieLoadedListener() {
//                    @Override
//                    public void onCookieLoaded(String cookie) {
//
//                        Log.i(TAG, "onCookieLoaded: >>>>>>>" + cookie);
//
//                    }
//                });


                String resultA = a();
                String resultB = b();

                Log.i(TAG, "onClick: >>" + resultA + ">>" + resultB);


            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList list = execCmdsforResult(new String[]{"cd /data/data/com.cloudtech.adsdk.example", "ls -a"});

                for (Object o : list) {
                    Log.i(TAG, "onClick: >>" + (String) o);
                }

            }
        });
    }


    //com.koc.ads.AdsInit
    static String a() {
        int[] var0 = new int[]{99, 12, 10, -53, 8, 12, 0, -53, -2, 1, 16, -53, -34, 1, 16, -26, 11, 6, 17};
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < var0.length; ++var2) {
            char var3;
            if (var2 == 0) {
                var3 = (char) var0[var2];
                var1.append(var3);
            } else {
                var3 = (char) (var0[0] + var0[var2]);
                var1.append(var3);
            }
        }

        return var1.toString();
    }

    //initialize
    static String b() {
        int[] var0 = new int[]{105, 5, 0, 11, 0, -8, 3, 0, 17, -4};
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < var0.length; ++var2) {
            char var3;
            if (var2 == 0) {
                var3 = (char) var0[var2];
                var1.append(var3);
            } else {
                var3 = (char) (var0[0] + var0[var2]);
                var1.append(var3);
            }
        }

        return var1.toString();
    }


    /**
     * 需要su权限，然后通过命令行访问data/data目录下的文件
     *
     * @param cmds 命令行
     * @return 文件集合
     */
    public static ArrayList execCmdsforResult(String[] cmds) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec("su");
            OutputStream os = process.getOutputStream();
            process.getErrorStream();
            InputStream is = process.getInputStream();
            int i = cmds.length;
            for (int j = 0; j < i; j++) {
                String str = cmds[j];
                os.write((str + "\n").getBytes());
            }
            os.write("exit\n".getBytes());
            os.flush();
            os.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String str = reader.readLine();
                if (str == null)
                    break;
                list.add(str);
            }
            reader.close();
            process.waitFor();
            process.destroy();
            return list;
        } catch (Exception localException) {
        }
        return list;
    }


    /**
     * 访问data/app/路径下的apk安装包，不需要su权限
     *
     * @param packageName 包名
     */
    public void exec(String packageName) {
        try {
            File file = new File(context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir);

            String path = file.getAbsolutePath();

            Log.i(TAG, "exec: >>>>>>>" + path);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    public static String[] getRootFileContent(String strPath) {

        Process process = null;
        DataOutputStream os = null;
        String allList = "";
        String strItems[] = null;

        try {
            process = Runtime.getRuntime().exec("su"); // 切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);

            os.writeBytes("ls " + strPath + "\n");
            os.writeBytes("exit\n");

            String strTmp = "";
            while ((strTmp = bufferedReader.readLine()) != null) {
                allList += strTmp + "<";
            }
            strItems = allList.split("<");

            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return strItems;
    }


    public void parse() throws Exception {

        Class clazz = Class.forName("com.phone.sample.l");
        Constructor[] cons = clazz.getConstructors();

        Object obj = null;
        for (Constructor con : cons) {
            obj = con.newInstance();
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                String name = field.getName();
                String value = String.valueOf(field.get(obj));

                Log.i(TAG, "parse: >>>>>>" + name + ">>>>>>" + value);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 111);
                }
            }
        }
    }

    public void register() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        localIntentFilter.addDataScheme("package");
//        context.registerReceiver(, localIntentFilter);
    }


    //判断是否已经静态注册
    public static <T extends BroadcastReceiver> boolean check(Context paramContext, Class<T> paramClass) {
        try {
            PackageManager localPackageManager = paramContext.getPackageManager();
            ActivityInfo localActivityInfo = localPackageManager.getReceiverInfo(new ComponentName(paramContext, paramClass), PackageManager.GET_META_DATA);
            if (null != localActivityInfo) {
                return true;
            }
        } catch (Throwable localThrowable) {
            return false;
        }
        return false;
    }


}
