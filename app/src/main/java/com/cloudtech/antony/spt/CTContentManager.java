package com.cloudtech.antony.spt;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.cloudtech.antony.CTLog;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static android.text.TextUtils.isEmpty;

public class CTContentManager {

    private static final String TAG = "CTContentRes";
    private static CTContentManager sInstance;
    private Context context;
    private ConcurrentHashMap<String, Long> downloadMap;


    private CTContentManager(Context context) {
        this.context = context;
        downloadMap = new ConcurrentHashMap<>();
    }


    public static CTContentManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CTContentManager.class) {
                if (null == sInstance) {
                    sInstance = new CTContentManager(context);
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

                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(Long.valueOf(id));

                    localCursor = downloadManager.query(query);


                    if (localCursor != null) {
                        while (localCursor.moveToNext()) {
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
                    localThrowable2.printStackTrace();
                } finally {
                    try {
                        if ((localCursor != null) && (!localCursor.isClosed())) {
                            localCursor.close();
                        }
                    } catch (Throwable localThrowable4) {
                        localThrowable4.printStackTrace();
                    }
                }

            }
        }
    }
}
