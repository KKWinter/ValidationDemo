package com.jumpraw.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MWebChromeClient extends WebChromeClient {


    private Context context;

    public MWebChromeClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

        Log.i("chromeclient", "onConsoleMessage: >>>>" + consoleMessage.message());

        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

        Log.i("chromeclient", "onProgressChanged: ===" + newProgress);

        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    // For Android >= 5.0
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
        return true;
    }


    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        Log.i("chrome", "onJsAlert: >> " + message);


        //默认系统的处理方式
//        return super.onJsAlert(view, url, message, result);


        //拦截JavaScript弹窗，自己弹窗
        new AlertDialog.Builder(context)
                .setTitle("JsAlert")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setCancelable(false)
                .show();
        return true;
        //true，拦截JavaScript的弹窗。如果拦截了，不会出现弹窗。
        //false，不拦截JavaScript的弹窗，由WebView自行决定弹窗。
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        Log.i("chrome", "onJsConfirm: >> " + message);

        //默认系统的处理方式
//        return super.onJsConfirm(view, url, message, result);

        //拦截JavaScript弹窗，自己弹窗
        new AlertDialog.Builder(context)
                .setTitle("JsConfirm")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                })
                .setCancelable(false)
                .show();

        return true;
        //true，拦截JavaScript的弹窗。如果拦截了，不会出现弹窗。
        //false，不拦截JavaScript的弹窗，由WebView自行决定弹窗。

    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        Log.i("chrome", "onJsPrompt: >> " + message);
//        return super.onJsPrompt(view, url, message, defaultValue, result);

        final EditText et = new EditText(context);
        et.setText(defaultValue);
        new AlertDialog.Builder(context)
                .setTitle(message)
                .setView(et)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm(et.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                })
                .setCancelable(false)
                .show();

        return true;
        //true，拦截JavaScript的弹窗。如果拦截了，不会出现弹窗。
        //false，不拦截JavaScript的弹窗，由WebView自行决定弹窗。
    }


}

