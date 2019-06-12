package com.kkwinter.thirdsdk.advworkflow.b;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public final class i
        extends a {
    private static WebView k = null;
    private Runnable l = null;

    public i(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, JSONObject paramJSONObject, String paramString5, HashMap<String, Object> paramHashMap, aa parama) {
        super(paramContext, paramString1, paramString2, paramString3, paramString4, paramLong, paramJSONObject, paramString5, paramHashMap, parama);
    }

    protected static void a(WebView paramWebView) {
        if (paramWebView == null) {
            return;
        }
        try {
            paramWebView.removeAllViews();
            paramWebView.freeMemory();
            paramWebView.onPause();
            paramWebView.destroy();
            return;
        } catch (Exception e) {
        }
    }

    private String b() {
        ArrayList localArrayList = c();
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        while (i < localArrayList.size()) {
            localStringBuilder.append("<script src=\"" + (String) localArrayList.get(i) + "\"></script>\n");
            i += 1;
        }
        return String.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<title>Untitled Document</title>\n</head>\n<body>\n%s</body>\n</html>\n", new Object[]{localStringBuilder.toString()});
    }

    private ArrayList<String> c() {
        ArrayList<String> localArrayList = new ArrayList();
        try {
            JSONArray localJSONArray = com.kkwinter.thirdsdk.advworkflow.util.k.a(com.kkwinter.thirdsdk.advworkflow.util.k.a(this.i, "task", (JSONObject) null), "urls", new JSONArray());
            int i = 0;
            while (i < localJSONArray.length()) {
                localArrayList.add(localJSONArray.getString(i));
                i += 1;
            }
            return localArrayList;
        } catch (Exception localException) {
            return localArrayList;
        }

    }

    public final void a() {

        Object localObject = new StringBuilder();
        WebView localWebView = a(this.a, new a.c(), (StringBuilder) localObject);
        k = localWebView;
        if (localWebView == null) {
            a(0, "创建webview失败 (" + ((StringBuilder) localObject).toString() + ")");
            return;
        }

        k.setWebViewClient(new WebViewClient() {
            public final void onPageFinished(final WebView paramAnonymousWebView, String paramAnonymousString) {

                if (l != null) {
                    com.kkwinter.thirdsdk.advworkflow.util.j.b(l);
                    i.this.l = null;
                }

                l = new Runnable() {
                    public final void run() {

                        a(1, "");
                        com.kkwinter.thirdsdk.advworkflow.b.i.a(paramAnonymousWebView);

                    }
                };

                com.kkwinter.thirdsdk.advworkflow.util.j.a(i.this.l, 120000L);
            }
        });

        String var3 = this.b();
        k.loadData(var3, "text/html", "utf-8");
    }
}

