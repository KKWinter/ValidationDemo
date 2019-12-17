package com.jumpraw.mraid.cache;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.text.TextUtils;
import android.util.Log;

import com.jumpraw.mraid.Utils.ContextHolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class UrlConnectionDownloader {
    private static final String RESPONSE_SOURCE = "X-Android-Response-Source";
    private static volatile Object cache;

    private static final Object lock = new Object();
    private static final int DEFAULT_READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s

    private static final String REQUEST_METHOD = "GET";


    public static UrlConnectionDownloader getInstance() {
        return SingleTonHolder.INSTANCE;
    }


    private static class SingleTonHolder {
        private static UrlConnectionDownloader
            INSTANCE = new UrlConnectionDownloader();
    }


    private UrlConnectionDownloader() {

    }


    private HttpURLConnection openConnection(String path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(path).openConnection();
        if (connection instanceof HttpsURLConnection) {
            SSLSocketFactory sslSocketFactory = SSLUtils.defaultSSLSocketFactory();
            ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
            HostnameVerifier hostnameVerifier = SSLUtils.defaultHostnameVerifier();
            if (hostnameVerifier != null) {
                ((HttpsURLConnection) connection).setHostnameVerifier(hostnameVerifier);
            }
        }
        connection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS);
        connection.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLIS);
        return connection;
    }


    public DownloadResult load(String requestUrl, Map<String, String> headers)
        throws RedirectsException, IOException {
        installCacheIfNeeded(ContextHolder.getGlobalAppContext());
        HttpURLConnection connection = openConnection(requestUrl);
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setUseCaches(true);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        int responseCode = connection.getResponseCode();
        if (responseCode == 301 || responseCode == 302) {
            connection.disconnect();
            String newUri = connection.getHeaderField("Location");
            if (!TextUtils.isEmpty(newUri)) {
                Log.w("Zcoup", "Uri redirect, newUri is " + newUri);
                throw new RedirectsException(newUri);
            } else {
                Log.w("Zcoup",
                    String.format("Uri redirects failed. newUri is empty, originUri: %s",
                        requestUrl));
            }
        }
        String responseMsg = connection.getResponseMessage();
        InputStream inputStream = connection.getInputStream();
        String headerField = connection.getHeaderField(RESPONSE_SOURCE);
        boolean fromCache = Util.parseResponseSourceHeader(headerField);
        Map<String, String> responseHeaders = new HashMap<>();
        String encoding = "UTF-8";
        if (connection.getContentEncoding() != null) {
            encoding = connection.getContentEncoding();
        }
        String contentType = connection.getContentType();
        Map<String, List<String>> map = connection.getHeaderFields();

        if (map != null) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                List<String> value = entry.getValue();
                if (!value.isEmpty()) {
                    responseHeaders.put(entry.getKey(), value.get(0));
                }
            }
        }
        return new DownloadResult(responseCode, responseMsg, inputStream, responseHeaders, encoding,
            contentType, fromCache);
    }


    public void shutdown() {
        if (cache != null) {
            ResponseCacheIcs.close(cache);
        }
    }


    private static void installCacheIfNeeded(Context context) {
        // DCL + volatile should be safe after Java 5.
        if (cache == null) {
            try {
                synchronized (lock) {
                    if (cache == null) {
                        cache = ResponseCacheIcs.install(context);
                    }
                }
            } catch (IOException ignored) {
            }
        }
    }


    private static class ResponseCacheIcs {
        static Object install(Context context) throws IOException {
            File cacheDir = Util.createDefaultCacheDir(context);
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache == null) {
                long maxSize = Util.calculateDiskCacheSize(cacheDir);
                cache = HttpResponseCache.install(cacheDir, maxSize);
            }
            return cache;
        }


        static void close(Object cache) {
            try {
                ((HttpResponseCache) cache).close();
            } catch (IOException ignored) {
            }
        }
    }
}
