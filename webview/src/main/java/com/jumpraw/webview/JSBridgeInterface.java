package com.jumpraw.webview;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class JSBridgeInterface {
    private static final String INTERFACE_NAME = "jsGaea";

    @SuppressLint("AddJavascriptInterface")
    protected static void addToWebview(WebView webView) {
        JSBridgeInterface jsb = new JSBridgeInterface(webView.getContext().getApplicationContext());
        webView.addJavascriptInterface(jsb, INTERFACE_NAME);
    }

    private Context mContext = null;
    private static String gaid = "";
    private static String uid = "";
    private final static String CHARSET_NAME = "utf-8";
    private File saveFile = null;
    private String picCode = "";

    private JSBridgeInterface(Context context) {
        mContext = context;
        saveFile = new File(context.getFilesDir().getAbsolutePath() + "/wxg_2078");

        //获取gaid
        gaid = trimString(readFile(saveFile));
        if (gaid.isEmpty()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String temp = trimString(getGaid(mContext));
                    if (!temp.isEmpty()) {
                        if (!gaid.equals(temp)) {
                            gaid = temp;
                            try {
                                writeFile(saveFile, gaid.getBytes(CHARSET_NAME));
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }

        //获取uid
        getUid(context);//Updated
    }


    @JavascriptInterface
    public void printLog() {
        Log.i("from bridge", "pinrtLog: >>>>");



    }

    @JavascriptInterface
    public String getInfo()//获取网络状态等信息
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", getNetworkType(mContext));
            jsonObject.put("uid", trimString(getUid(mContext)));//Updated
            jsonObject.put("gaid", trimString(gaid));
        } catch (Exception e) {
        }
        return jsonObject.toString();
    }

    @JavascriptInterface
    public String getPicCode()//获取结果
    {
        return trimString(picCode);
    }

    @JavascriptInterface
    public void doPic(final String url, final String headers, final String uurl)//处理图片验证码
    {
        try {
            JSONObject hObj = null;
            try {
                hObj = new JSONObject(headers);
            } catch (Exception e) {
            }

            final JSONObject jsonObject = hObj;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    simpleGet(url, null, jsonObject, new Callback() {
                        @Override
                        public void onSucc(int code, InputStream inputStream, long contentLength) {
                            try {
                                byte[] data = getBytes(inputStream);
                                if ((data != null) && (data.length > 0)) {
                                    simplePost(uurl, null, jsonObject, data, new Callback() {
                                        @Override
                                        public void onSucc(int code, InputStream inputStream, long contentLength) {
                                            try {
                                                picCode = new String(getBytes(inputStream), CHARSET_NAME);
                                            } catch (Exception e) {
                                            }
                                        }
                                    });
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
        }
    }

    @JavascriptInterface
    public void upload(final String url, final String content, final int compressType)//抓页面数据用,1为zip,其它为不压缩
    {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] body = (content == null ? "" : content.trim()).getBytes(CHARSET_NAME);
                        if (compressType == 1) {
                            body = compress(body);
                        }
                        simplePost(url, null, null, body, null);
                    } catch (Exception e) {
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
        }
    }


    //--------------------------------------------------other utils--------------------------------------------------
    private static String trimString(String s) {
        return (s == null ? "" : s.trim());
    }


    private static String getUid(Context c)//Updated
    {
        try {
            if (TextUtils.isEmpty(uid)) {
                File ff = new File("/wxg_2088");
                uid = readFile(ff);
                uid = (uid == null) ? "" : uid.trim();
                if (uid.isEmpty()) {
                    try {
                        uid = getAndroidId(c);
                    } catch (Exception e) {
                    }
                    if (TextUtils.isEmpty(uid)) {
                        UUID uuid = UUID.randomUUID();
                        uid = uuid.toString().replace("-", "");
                    }
                    if (!TextUtils.isEmpty(uid)) {
                        writeFile(ff, uid.getBytes(CHARSET_NAME));
                    }
                }
            }
            return uid;
        } catch (Exception e) {
            return null;
        }
    }

    //private static String getImsi(Context context){}//Updated

    // Android Id
    private static String getAndroidId(Context context) {//Updated
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }


    private static int getNetworkType(Context c) {
        try {
            return ((ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().getType();
        } catch (Exception e) {
            return -9999;
        }
    }

    private static byte[] compress(byte[] bytes) {
        if (bytes == null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        byte[] arrayOfByte = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            zipOutputStream.putNextEntry(new ZipEntry("0"));
            zipOutputStream.write(bytes);
            zipOutputStream.closeEntry();
            arrayOfByte = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
        } finally {
            try {
                zipOutputStream.close();
            } catch (Exception e) {
            }
            try {
                byteArrayOutputStream.close();
            } catch (Exception localIOException7) {
            }
        }
        return arrayOfByte;
    }

    //--------------------------------------------------file utils--------------------------------------------------
    private static void writeFile(File file, byte[] bytes) {
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(file);
            fo.write(bytes);
        } catch (Exception e) {
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static String readFile(File file) {
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(file);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count;
            while ((count = fi.read(data, 0, data.length)) != -1) {
                outStream.write(data, 0, count);
            }
            return new String(outStream.toByteArray(), CHARSET_NAME);
        } catch (Exception e) {
        } finally {
            if (fi != null) {
                try {
                    fi.close();
                } catch (Exception e) {
                }
            }
        }
        return "";
    }

    private static byte[] getBytes(InputStream ipt) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count;
            while ((count = ipt.read(data, 0, data.length)) != -1) {
                outStream.write(data, 0, count);
            }
            return outStream.toByteArray();
        } catch (Exception e) {
        } finally {
            try {
                ipt.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    //--------------------------------------------------http utils--------------------------------------------------
    private static class Callback {
        void onSucc(int code, InputStream inputStream, long contentLength) {
        }

        void onFail(int code) {
        }
    }

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }};
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    private static void simpleGet(String url, HashMap<String, String> params, JSONObject headers, Callback callback) {
        simpleHttpRequest(url, params, headers, null, callback, true);
    }

    private static void simplePost(String url, HashMap<String, String> params, JSONObject headers, byte[] body, Callback callback) {
        simpleHttpRequest(url, params, headers, body, callback, false);
    }

    private static void simpleHttpRequest(String url, HashMap<String, String> params, JSONObject headers, byte[] body, Callback callback, boolean isGet) {
        try {
            if (params != null) {
                Uri.Builder builder = Uri.parse(url).buildUpon();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
                url = builder.toString();
            }
            URL urlObject = new URL(url);
            HttpURLConnection connection = null;
            if (url.startsWith("https")) {
                HttpsURLConnection sconnection = null;
                trustAllHosts();
                connection = sconnection = (HttpsURLConnection) urlObject.openConnection();
                sconnection.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });
            } else {
                connection = (HttpURLConnection) urlObject.openConnection();
            }
            connection.setRequestMethod(isGet ? "GET" : "POST");
            connection.setConnectTimeout(60 * 1000);
            connection.setReadTimeout(60 * 1000);
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            if (body != null) {
                connection.setDoOutput(true);
                connection.addRequestProperty("Content-Type", "application/octet-stream");
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(body);
                out.close();
            }
            if (headers != null) {
                Iterator<String> iterator = headers.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (key != null) {
                        try {
                            connection.setRequestProperty(key, headers.getString(key));
                        } catch (Exception e) {
                        }
                    }
                }
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (callback != null) {
                if (responseCode >= 200 && responseCode <= 299) {//onSucc
                    try {
                        callback.onSucc(responseCode, connection.getInputStream(), connection.getContentLength());
                    } catch (Exception e) {
                    }
                } else {//onFail
                    try {
                        callback.onFail(responseCode);
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            if (callback != null) {
                try {
                    callback.onFail(-1);
                } catch (Exception e1) {
                }
            }
        }
    }

    //--------------------------------------------------gaid utils--------------------------------------------------
    private static String getGaid(Context c)//获取谷歌广告id
    {
        try {
            return trimString(AdvertisingIdClient.getAdvertisingId(c));
        } catch (Exception e) {
            return "";
        }
    }

    private static class AdvertisingIdClient {
        static String getAdvertisingId(Context context) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                return "";
            }

            try {
                PackageManager pm = context.getPackageManager();
                pm.getPackageInfo("com.android.vending", 0);
            } catch (Exception e) {
                return "";
            }

            AdvertisingConnection connection = new AdvertisingConnection();
            Intent intent = new Intent(
                    "com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (context.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
                try {
                    AdvertisingInterface adInterface = new AdvertisingInterface(
                            connection.getBinder());
                    return adInterface.getId();
                } catch (Exception exception) {
                    return "";
                } finally {
                    context.unbindService(connection);
                }
            }
            return "";
        }

        private static final class AdvertisingConnection implements
                ServiceConnection {
            boolean retrieved = false;
            private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue<IBinder>(
                    1);

            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    this.queue.put(service);
                } catch (InterruptedException localInterruptedException) {
                }
            }

            public void onServiceDisconnected(ComponentName name) {
            }

            public IBinder getBinder() throws InterruptedException {
                if (this.retrieved)
                    throw new IllegalStateException();
                this.retrieved = true;
                return (IBinder) this.queue.take();
            }
        }

        private static final class AdvertisingInterface implements IInterface {
            private IBinder binder;

            public AdvertisingInterface(IBinder pBinder) {
                binder = pBinder;
            }

            public IBinder asBinder() {
                return binder;
            }

            public String getId() throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                String id;
                try {
                    data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    binder.transact(1, data, reply, 0);
                    reply.readException();
                    id = reply.readString();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return id;
            }
        }
    }
}
