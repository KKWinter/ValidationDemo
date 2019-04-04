package com.phone.sample;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;
import static android.text.TextUtils.regionMatches;

public class CTContentRes {

    public static final String TAG = "CTContentRes";
    private static CTContentRes sInstance;
    private ConcurrentHashMap<String, Long> downloadMap;
    private boolean hasError;


    private CTContentRes() {
        hasError = false;
        downloadMap = new ConcurrentHashMap<>();
    }

    public static CTContentRes getInstance() {
        if (sInstance == null) {
            synchronized (CTContentRes.class) {
                if (null == sInstance) {
                    sInstance = new CTContentRes();
                }
            }
        }
        return sInstance;
    }


    public void init(Context context) {
        try {
            String path = "content://downloads/";
            context.getContentResolver().registerContentObserver(Uri.parse(path), true, new CTContentObserver(context, new Handler(Looper.getMainLooper())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private final class CTContentObserver extends ContentObserver {
        Context context;
        String path = "content://downloads/public_downloads/";

        CTContentObserver(Context context, Handler handler) {
            super(handler);
            this.context = context;
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

            List<String> localList = uri.getPathSegments();
            for (String s : localList) {
                Log.i(TAG, "onChange: >>>>>>" + s);
            }


            if (localList.size() > 0) {
                String id = localList.get(localList.size() - 1);
                if ((TextUtils.isEmpty(id)) || (id.contains("down"))) {
                    return;
                }

                Cursor localCursor = null;
                try {
                    String contentPath = path;
                    if (isEmpty(contentPath)) {
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
                                    Log.i(TAG, "download list containsKey");
                                    return;
                                }
                            }

                            downloadMap.put(contentPath, System.currentTimeMillis());
                            Log.i(TAG, "query downloader manager package success");

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
                            // title = "scanguard";
                            if (!isEmpty(title)) {
                                jsonObject.put("title", title);
                            }

                            String data = localCursor.getString(
                                    localCursor.getColumnIndex("_data"));
                            if (!isEmpty(data)) {
                                jsonObject.put("_data", data);
                            }

                            String totalBytes = localCursor.getString(
                                    localCursor.getColumnIndex("total_bytes"));
                            if (!isEmpty(totalBytes)) {
                                jsonObject.put("total_bytes", totalBytes);
                            }

                            String description = localCursor.getString(
                                    localCursor.getColumnIndex("description"));
                            if (!isEmpty(description)) {
                                jsonObject.put("description", description);
                            }

                            String displayName = localCursor.getString(
                                    localCursor.getColumnIndex("_display_name"));
                            if (!isEmpty(displayName)) {
                                jsonObject.put("_display_name", displayName);
                            }

                            String mediaProviderUri = localCursor.getString(
                                    localCursor.getColumnIndex("mediaprovider_uri"));
                            if (!isEmpty(mediaProviderUri)) {
                                jsonObject.put("mediaprovider_uri", mediaProviderUri);
                            }

                            String entity = localCursor.getString(
                                    localCursor.getColumnIndex("entity"));
                            if (!isEmpty(entity)) {
                                jsonObject.put("entity", entity);
                            }

                            String control = localCursor.getString(
                                    localCursor.getColumnIndex("control"));
                            if (!isEmpty(control)) {
                                jsonObject.put("control", control);
                            }

                            String hint = localCursor.getString(localCursor.getColumnIndex("hint"));
                            if (!isEmpty(hint)) {
                                jsonObject.put("hint", hint);
                            }

                            String isVisibleInDownloadsUi = localCursor.getString(
                                    localCursor.getColumnIndex("is_visible_in_downloads_ui"));
                            if (!isEmpty(isVisibleInDownloadsUi)) {
                                jsonObject.put("is_visible_in_downloads_ui",
                                        isVisibleInDownloadsUi);
                            }

                            String _id = localCursor.getString(localCursor.getColumnIndex("_id"));
                            if (!isEmpty(_id)) {
                                jsonObject.put("_id", _id);
                            }

                            String mimeType = localCursor.getString(
                                    localCursor.getColumnIndex("mimetype"));
                            if (!isEmpty(mimeType)) {
                                jsonObject.put("mimetype", mimeType);
                            }

                            String lastMod = localCursor.getString(
                                    localCursor.getColumnIndex("lastmod"));
                            if (!isEmpty(lastMod)) {
                                jsonObject.put("lastmod", lastMod);
                            }

                            String deleted = localCursor.getString(
                                    localCursor.getColumnIndex("deleted"));
                            if (!isEmpty(deleted)) {
                                jsonObject.put("deleted", deleted);
                            }

                            String notificationClass = localCursor.getString(
                                    localCursor.getColumnIndex("notificationclass"));
                            if (!isEmpty(notificationClass)) {
                                jsonObject.put("notificationclass", notificationClass);
                            }

                            String currentBytes = localCursor.getString(
                                    localCursor.getColumnIndex("current_bytes"));
                            if (!isEmpty(currentBytes)) {
                                jsonObject.put("current_bytes", currentBytes);
                            }

                            String destination = localCursor.getString(
                                    localCursor.getColumnIndex("destination"));
                            if (!isEmpty(destination)) {
                                jsonObject.put("destination", destination);
                            }

                            Log.i(TAG, "notificationPackage " + notificationPackage);
                            Log.i(TAG, "uri " + downUrl);
                            Log.i(TAG, "title " + title);
                        }
                    }
                } catch (Throwable localThrowable2) {
                    hasError = true;
                    Log.e(TAG, "errMsg :" + Log.getStackTraceString(localThrowable2));
                } finally {
                    try {
                        if ((localCursor != null) && (!localCursor.isClosed())) {
                            localCursor.close();
                        }
                    } catch (Throwable localThrowable4) {
                        hasError = true;
                        Log.e(TAG, "errMsg :" + Log.getStackTraceString(localThrowable4));
                    }
                }

            }

        }
    }
}
