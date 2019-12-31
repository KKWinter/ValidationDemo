package com.jumpraw.webview;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MWebViewClient extends WebViewClient {

    private static final String TAG = "MWebViewClient";

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i(TAG, "onPageStarted: >>" + url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, String url) {
        Log.i(TAG, "shouldOverrideUrlLoading: 1>>" + url);


        Uri uri = Uri.parse(url);
        if (uri.getScheme().equals("js") && uri.getAuthority().equals("webview")) {

            Log.i(TAG, "JS调用到了android的方法里边，可以调用js对应的native方法了");
        }


        if (!url.startsWith("http") && !url.startsWith("https")) {
            return true;
        }


        if (url.startsWith("vipshop")) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ComponentName componentName = intent.resolveActivity(App.context.getPackageManager());
            if (componentName != null) {
                App.context.startActivity(intent);
            } else {
                // app不存在
            }

            return true;
        }

        view.loadUrl(url);
        return true;

//        return super.shouldOverrideUrlLoading(view, url);
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "onPageFinished: >>" + url);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        String result = request.getUrl().toString();

        return super.shouldInterceptRequest(view, request);
    }
}

