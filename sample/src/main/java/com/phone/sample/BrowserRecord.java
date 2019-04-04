package com.phone.sample;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


/**
 * Created by huangdong on 2019/1/24.
 * antony.huang@yeahmobi.com
 */
public class BrowserRecord {

    private static final String TAG = "BrowserRecord";

    private static String systembrowser1 = "content://browser";
    private static String systemmarkbooks1 = "content://browser/bookmarks";

    private static String sysytembrowser2 = "content://com.android.borwser";
    private static String systemmarkbooks2 = "content://com.android.borwser/bookmarks";


    private static String miuiBrowser = "content://miuibrowser";
    private static String miuiBrowserMarkBooks = "content://miuibrowser/bookmarks";


    private static String chrome = "content://com.android.chrome.browser";
    private static String chromeMarkBooks = "content://com.android.chrome.browser/bookmarks";


    private static String liebaoBrowser = "content://com.ijinshan.browser.android.provider.BrowserWebViewProvider";
    private static String liebaoMarkbooks = "content://com.ijinshan.browser.android.provider.BrowserWebViewProvider/bookmarks";


    private static String sanxingbookmarks = "content://com.sec.android.app.sbrowser.ChromeBrowserProvider/bookmarks";


    private static String vivobookmarks = "content://com.vivo.browser/bookmarks";


    private static BrowserRecord browserRecord;

    public static BrowserRecord getInstance() {
        if (browserRecord == null) {
            browserRecord = new BrowserRecord();
        }
        return browserRecord;
    }


    public void getRecords(final Context context) {

        new Thread(){
            @Override
            public void run() {

                Log.i(TAG, "run: >>>>>>>>>>");

                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver.query(Uri.parse(miuiBrowserMarkBooks), null, null, null, null);
                while (cursor != null && cursor.moveToNext()) {
                    String url = null;
                    String title = null;

                    title = cursor.getString(cursor.getColumnIndex("title"));
                    url = cursor.getString(cursor.getColumnIndex("url"));


                    Log.i(TAG, "getRecords: >>>>" + title + ">>>>" + url);
                }


            }
        }.start();


    }









    public void init(Context context) {

        try {
            context.getContentResolver()
                    .registerContentObserver(Uri.parse(chrome), true, new MyContentObserver(new Handler(Looper.getMainLooper())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);


            Log.i(TAG, "onChange: >>>>" + uri);

        }


    }

}
