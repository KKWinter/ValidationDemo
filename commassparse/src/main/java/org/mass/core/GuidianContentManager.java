package org.mass.core;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.mass.content.Content;
import org.mass.content.ContentConfig;
import org.mass.content.ContentFormat;
import org.mass.content.ContentParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by zhj_7 on 2016/11/18.
 */

public class GuidianContentManager {
    private final static String TAG = "GCM";
    private Service service;
    private Context context;
    private Content content;
    private ContentFormat contentFormat;
    private Object contentObject;
    private Class<?> cls;
    private Class<?> custClass;

    public GuidianContentManager(Service service, Class<?> cust) {
        this.service = service;   //MassEService
        custClass = cust;         //MassService.class
        ProcessContext.clear(this.getClass().getName());
        context = service.getApplicationContext();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    load();
                    break;

                case 2:
                    break;
            }
        }
    };


    private void load() {
        try {
            Loader loader = new Loader(service.getApplicationContext(), content.path + File.separator + content.rfName);
            ProcessContext.setLoader(this.getClass().getName(), loader);
            //Loader loader = Loader.getInstance(service.getApplicationContext(), content.path + File.separator + content.rfName);
    		Log.i(TAG, "load class: " + contentFormat.cls);
    		//类： com.android.settings.allinone.services.main.MainService
            cls = loader.load(contentFormat.cls);
	        Log.i(TAG, "class loaded: " + cls);

            contentObject = cls.newInstance();
	        Log.i(TAG, "class instance created.");

	        Log.i(TAG, "get class method: " + contentFormat.entry);
	        //方法： onCreate
            Method entry = cls.getDeclaredMethod(contentFormat.entry, new Class<?>[]{Service.class, Class.class});
	        Log.i(TAG, "method find: " + entry);

	        //参数： MassEService     MassService.class
            entry.invoke(contentObject, new Object[]{service, custClass});
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ContentConfig.clearConfig(context);
        }
    }

    public void loadContent() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "start to load content");
                content = getContent();

                if (content != null) {
                    try {
                        analyze();
                        mHandler.sendEmptyMessage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //parse error, clear content config, load original dynamic file.
                        ContentConfig.clearConfig(context);
                        close();
                    }
                } else {
                    close();
                }
            }
        });
    }


    public void releaseContent() {
        try {
            Method finallizer = cls.getDeclaredMethod(contentFormat.finallizer, new Class<?>[]{});
            finallizer.invoke(contentObject, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解密文件
    public void analyze() throws Exception {
        if (isContentExist()) {
            //原文件
            String src = content.path + File.separator + content.fileName;
            //要解密成的文件
            String release = content.release + File.separator + content.rfName;

            ContentParser parser = new ContentParser(context, src, release);
            parser.parse();
            if (parser.isSuccess()) {
                contentFormat = parser.getFormat();
            } else {
                throw new InvalidPropertiesFormatException("parse file failed");
            }
        } else {
            throw new IllegalStateException("can not find content file.");
        }
    }

    private void close() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public boolean isContentExist() {
        File file = new File(content.path + File.separator + content.fileName);
        return file.exists() && file.isFile();
    }


    //获取assets中文件
    private Content getContent() {
        Content content = getLocal();

        if (content == null) {
            throw new IllegalStateException("can not find local data.");
        }

        return content;
    }

    private Content getLocal() {
        //写文件
        copySource();

        //文件存储路径
        Content content = new Content();
        content.path = context.getFilesDir().getAbsolutePath();
        content.fileName = ".data.dat";
        content.release = content.path;
        content.rfName = ".data.jar";

        return content;
    }

    //把assets中的文件写到filedir目录下
    private void copySource() {
        AssetManager am = context.getAssets();
        InputStream is = null;
        FileOutputStream fos = null;

        String dest = context.getFilesDir().getAbsolutePath() + File.separator + ".data.dat";
        Log.i(TAG, "copySource: >>>>>" + dest);

        File file = new File(dest);
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        try {
            is = am.open("data.dat");
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[10 * 1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
