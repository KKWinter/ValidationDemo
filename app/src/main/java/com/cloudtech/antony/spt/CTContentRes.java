package com.cloudtech.antony.spt;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.cloudtech.antony.utils.CTLog;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

public class CTContentRes {

    private static final String TAG = "CTContentRes";
    private static CTContentRes sInstance;
    private Context context;
    private boolean hasError;
    private ConcurrentHashMap<String, Long> downloadMap;


    private CTContentRes(Context context) {
        this.context = context;
        hasError = false;
        downloadMap = new ConcurrentHashMap<>();
    }


    public static CTContentRes getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CTContentRes.class) {
                if (null == sInstance) {
                    sInstance = new CTContentRes(context);
                }
            }
        }
        return sInstance;
    }


    public void init() {
        try {
            //content://downloads
            String path = "content://downloads";
            context.getContentResolver()
                    .registerContentObserver(Uri.parse(path), true,
                            new CTContentObserver(new Handler(Looper.getMainLooper())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private final class CTContentObserver extends ContentObserver {

        CTContentObserver(Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }


        @Override
        public void onChange(boolean selfChange, final Uri uri) {
            super.onChange(selfChange, uri);
            if (uri == null) {
                return;
            }

            CTLog.d(TAG, "=======" + uri);

            List localList = uri.getPathSegments();
            if (localList != null && localList.size() > 0) {
                String id = (String) localList.get(localList.size() - 1);
                if ((TextUtils.isEmpty(id)) || (id.contains("down"))) {
                    return;
                }

                Cursor localCursor = null;
                try {
                    //content://downloads/public_downloads
                    String contentPath = "content://downloads/public_downloads/";
                    if (isEmpty(contentPath) || hasError) {
                        return;
                    }

                    contentPath = contentPath + id;
                    Uri localUri = Uri.parse(contentPath);
                    ContentResolver localContentResolver = context.getContentResolver();
                    localCursor = localContentResolver.query(localUri, null, null, null, null);
                    if (localCursor != null) {
                        while (localCursor.moveToNext()) {
                            if (downloadMap == null) {
                                downloadMap = new ConcurrentHashMap<>();
                            }
                            if (!downloadMap.isEmpty()) {
                                if (downloadMap.containsKey(contentPath)) {
                                    CTLog.d(TAG, "download list containsKey");
                                    return;
                                }
                            }

                            downloadMap.put(contentPath, System.currentTimeMillis());
                            CTLog.d(TAG, "query downloader manager package success");


                            JSONObject jsonObject = new JSONObject();

                            String notificationPackage = localCursor.getString(
                                    localCursor.getColumnIndex("notificationPackage"));
                            if (!isEmpty(notificationPackage)) {
                                jsonObject.put("notificationPackage", notificationPackage);
                            }

                            String downUrl = localCursor.getString(
                                    localCursor.getColumnIndex("uri"));
                            if (!isEmpty(downUrl)) {
                                jsonObject.put("uri", downUrl);
                            }

                            String title = localCursor.getString(
                                    localCursor.getColumnIndex("title"));
                            if (!isEmpty(title)) {
                                jsonObject.put("title", title);
                            }


                            CTLog.d(TAG, "notificationPackage " + notificationPackage);
                            CTLog.d(TAG, "uri " + downUrl);
                            CTLog.d(TAG, "title " + title);
                        }
                    }
                } catch (Throwable localThrowable2) {
                    hasError = true;
//                    localThrowable2.printStackTrace();
                } finally {
                    try {
                        if ((localCursor != null) && (!localCursor.isClosed())) {
                            localCursor.close();
                        }
                    } catch (Throwable localThrowable4) {
                        hasError = true;
//                        localThrowable4.printStackTrace();
                    }
                }

            }
        }
    }
}
