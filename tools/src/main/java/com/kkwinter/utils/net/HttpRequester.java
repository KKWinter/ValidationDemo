package com.kkwinter.utils.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kkwinter.utils.ThreadPoolProxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;

public class HttpRequester {

    public interface Listener {
        void onGetDataSucceed(byte[] data);

        void onGetDataFailed(String error);
    }

    public static void executeAsync(Context context, String url, Listener listener) {
        getAsyncData(context, url, listener, RequestMethod.GET,
                null);
    }


    public static void executeAsyncByPost(Context context, String url, String postBody, Listener listener) {
        getAsyncData(context, url, listener, RequestMethod.POST,
                postBody);
    }


    private static void getAsyncData(Context context, String url, Listener listener, RequestMethod method, String postBody) {
        HttpRunnable runnable;
        if (method == RequestMethod.GET) {
            runnable = new HttpRunnable(url, listener, context);
        } else {
            runnable = new HttpRunnable(url, listener, context, method, postBody);
        }

        ThreadPoolProxy.getInstance().execute(runnable);
    }
}


class HttpRunnable implements Runnable {

    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static final int SOCKET_TIMEOUT = 1000 * 30;
    private static final String TAG = "HttpRunnable";
    private String urlString = null;
    private RequestMethod method = RequestMethod.GET;
    private String requestBody;
    private HashMap<String, Object> data;

    public HttpRunnable(String url, HttpRequester.Listener listener, Context context) {
        this.urlString = url;
        data = new HashMap<>();
        data.put("callback", listener);
    }

    public HttpRunnable(String url, HttpRequester.Listener listener, Context context,
                        RequestMethod method, String body) {
        this(url, listener, context);
        this.method = method;
        this.requestBody = body;
    }

    @Override
    public void run() {
        Message msg = new Message();
        HttpURLConnection connection = null;
        try {
            connection = handleConnection(urlString, method);
            byte[] bytes = handleSuccess(connection);
            msg.what = 0;
            data.put("data", bytes);
            msg.obj = data;
        } catch (HttpErrorException e) {
            e.printStackTrace();

            msg.what = 1;
            data.put("error", e.getMessage());
            msg.obj = data;
        } catch (HttpRedirectException e) {
            e.printStackTrace();

            msg.what = 3;
            msg.obj = data;
            data.put("error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            msg.what = 2;
            data.put("error", e.getMessage());
            msg.obj = data;
        } finally {
            handler.post(new ResultRunnable(msg));
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection handleConnection(String urlStr, RequestMethod method) throws Exception {
        HttpURLConnection conn;
        boolean redirected;

        int redirectCount = 0;
        do {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            if (conn instanceof HttpsURLConnection) {
                SSLSocketFactory sslSocketFactory = SSLUtils.defaultSSLSocketFactory();
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
                HostnameVerifier hostnameVerifier = SSLUtils.defaultHostnameVerifier();
                if (hostnameVerifier != null) {
                    ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
                }
            }
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(SOCKET_TIMEOUT);
            conn.setRequestProperty("Accept-Encoding", "gzip");
            conn.setRequestProperty("CT-Accept-Encoding", "gzip");
            conn.setRequestMethod(method.toString());
            conn.setDoInput(true);
            conn.setInstanceFollowRedirects(false);
            if (method == RequestMethod.POST) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-type", "application/json");
                OutputStream os = conn.getOutputStream();
                ByteArrayInputStream stream = new ByteArrayInputStream(requestBody.getBytes());
                int len;
                byte[] buffer = new byte[2048];
                while ((len = stream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                stream.close();
                os.flush();
                os.close();
            }
            int code = conn.getResponseCode();
            if (code >= HTTP_BAD_REQUEST) {
                throw new HttpErrorException("request error code : " + code);
            }
            redirected = code == HTTP_MOVED_TEMP;
            if (redirected) {
                urlStr = conn.getHeaderField("Location");
                redirectCount++;
                conn.disconnect();
            }
            int MAX_REDIRECTS = 10;
            if (redirectCount >= MAX_REDIRECTS) {
                throw new HttpRedirectException("Too many redirects: " + redirectCount);
            }
        } while (redirected);

        return conn;
    }


    private byte[] handleSuccess(HttpURLConnection conn) throws Exception {
        InputStream is = conn.getInputStream();

        if ("gzip".equals(conn.getContentEncoding())) {
            is = new GZIPInputStream(is);
        }

        byte[] dataBytes = getBytes(is);
        is.close();

        return dataBytes;
    }


    private byte[] getBytes(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        return baos.toByteArray();
    }


    private class HttpRedirectException extends Exception {
        public HttpRedirectException(String message) {
            super(message);
        }
    }


    private class HttpErrorException extends Exception {
        public HttpErrorException(String message) {
            super(message);
        }
    }
}


class ResultRunnable implements Runnable {
    private Message msg = null;
    private static final String UNKOWN_ERROR = "unkown error";

    public ResultRunnable(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) msg.obj;
            HttpRequester.Listener listener = (HttpRequester.Listener) data.get("callback");
            switch (msg.what) {
                case 0:
                    listener.onGetDataSucceed((byte[]) data.get("data"));
                    break;
                case 1:
                case 2:
                case 3:
                    listener.onGetDataFailed((String) data.get("error"));
                    break;
                default:
                    listener.onGetDataFailed(UNKOWN_ERROR);
                    break;
            }

        } catch (Exception e) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) msg.obj;
            if (data == null) {
                return;
            }
            HttpRequester.Listener listener = (HttpRequester.Listener) data.get("callback");
            if (listener != null) {
                listener.onGetDataFailed("HttpRequester get data error:" + e.getMessage());
            }

        }
    }
}
