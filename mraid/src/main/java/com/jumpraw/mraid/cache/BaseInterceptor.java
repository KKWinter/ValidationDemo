package com.jumpraw.mraid.cache;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.util.Map;

import androidx.annotation.NonNull;

public abstract class BaseInterceptor implements WebResInterceptor {

    private static final String RES_TYPE_HTML = "text/html";
    private static final String CHARSET_ENCODE = "UTF-8";
    private static final long DEFAULT_CACHE_DISK_SIZE = 10 * 1024 * 1024;
    private Handler handler = new Handler(Looper.getMainLooper());
    private OnErrorListener onErrorListener;
    private UrlConnectionDownloader downloader;

    public BaseInterceptor(@NonNull Context context) {
        downloader = UrlConnectionDownloader.getInstance();
    }

    protected abstract boolean isIntercept(String url, Map<String, String> headers);

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    @Override
    public WebResourceResponse intercept(Chain chain) {
        String url = chain.getRequestUrl();
        // if the current interceptor doesn't intercept the current request,pass to the next interceptor
        if (!isIntercept(url, chain.getRequestHeaders())) {
            return chain.proceed(url, chain.getRequestHeaders());
        }

        DownloadResult result = null;
        try {
            result = loopRetryLoad(chain);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // it seems that something wrong happened as the response code>=400
        if (result != null && result.responseCode >= 400 && onErrorListener != null) {
            dispatchError(url, result.responseCode, result.responseMsg);
        }
        return buildWebResponse(result);

    }


    private static int redirectCount = 0;
    private static final int MAX_REDIRECT_COUNT = 5;

    private DownloadResult loopRetryLoad(Chain chain) throws Exception {
        String url = chain.getRequestUrl();
        Map<String, String> headers = chain.getRequestHeaders();
        while (redirectCount <= MAX_REDIRECT_COUNT) {
            try {
                return downloader.load(url, headers);
            } catch (RedirectsException e) {
                url = e.getNewUrl();
                redirectCount++;
                Log.w("  Zcoup", "loopRetryLoad: redirectCount -> " + redirectCount);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        return null;
    }


    private WebResourceResponse buildWebResponse(DownloadResult result) {
        if (result == null) {
            return null;
        }
        if (result.contentType != null && result.contentType.toLowerCase().contains("html")) {
            result.contentType = RES_TYPE_HTML;
            result.contentEncoding = CHARSET_ENCODE;
        }
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? new WebResourceResponse(result.contentType, result.contentEncoding,
                result.responseCode, result.responseMsg, result.responseHeaders, result.inputStream)
                : new WebResourceResponse(result.contentType, "UTF-8", result.inputStream);
    }


    private void dispatchError(final String url, final int code, final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onErrorListener.onError(url, code, message);
            }
        });
    }


    public interface OnErrorListener {
        /**
         * meet an error when downloading the web resource
         *
         * @param requestUrl  the url of the web res
         * @param errorCode   error code
         * @param responseMsg error message
         */
        void onError(String requestUrl, int errorCode, String responseMsg);
    }
}
