package com.cloudtech.antony.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cloudtech.antony.sqlite.CreativeSqliteOpenHelper.CID;
import static com.cloudtech.antony.sqlite.CreativeSqliteOpenHelper.PLAYTIME;
import static com.cloudtech.antony.sqlite.CreativeSqliteOpenHelper.REF;
import static com.cloudtech.antony.sqlite.CreativeSqliteOpenHelper.TABLE_NAME;

/**
 * Created by huangdong on 17/12/5.
 * antony.huang@yeahmobi.com
 */
public class CreativeSqliteDao {
    private static final String TAG = "CreativeSqliteDao";

    private AtomicInteger mOpenCounter = new AtomicInteger();
    private CreativeSqliteOpenHelper creativeSqliteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    private static CreativeSqliteDao creativeSqliteDao;

    private CreativeSqliteDao(Context context) {
        creativeSqliteOpenHelper = new CreativeSqliteOpenHelper(context);
    }

    public synchronized static CreativeSqliteDao getInstance(Context context) {
        if (creativeSqliteDao == null) {
            creativeSqliteDao = new CreativeSqliteDao(context);
        }
        return creativeSqliteDao;
    }


    private synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            sqLiteDatabase = creativeSqliteOpenHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }


    private synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
    }


    /**
     * 向数据库中插入数据
     */
    public void insert(final List<CreativeVO> creatives) {

        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                SQLiteDatabase db = openDatabase();
                if (db == null || creatives == null || creatives.isEmpty()) {
                    return;
                }

                try {
                    db.beginTransaction();
                    for (CreativeVO creative : creatives) {
                        ContentValues contentValues = new ContentValues();
                        String cid = creative.getCid();
                        contentValues.put("cid", cid);

                        String url = creative.getUrl();
                        contentValues.put("url", url);

                        int status = creative.getStatus();
                        contentValues.put("status", status);

                        db.replace(TABLE_NAME, null, contentValues);
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.i(TAG, "insert error >>" + e.getMessage());
                } finally {
                    db.endTransaction();
                    closeDatabase();
                }

            }
        });

    }





    // 获取数据库中的素材集合
    public synchronized List<CreativeVO> queryAllLoaded() {
        List<CreativeVO> aList = new ArrayList<>();

        SQLiteDatabase db = openDatabase();
        if (db == null) {
            return aList;
        }

        String sql = "select * from creative where status = ?;";
        Cursor cursor = null;
        try {
            db.beginTransaction();

            cursor = db.rawQuery(sql, new String[]{"1"});
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    CreativeVO creative = new CreativeVO();

                    creative.setCid(cursor.getString(cursor.getColumnIndex("cid")));
                    creative.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                    creative.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                    aList.add(creative);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.i(TAG, "queryAllLoaded error >>" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
            closeDatabase();
        }

        return aList;
    }


    // 获取数据库中的素材集合
    public synchronized List<CreativeVO> deleteByType() {
        List<CreativeVO> aList = new ArrayList<>();

        SQLiteDatabase db = openDatabase();
        if (db == null) {
            return aList;
        }

        String sql = "select * from creative where status = ?;";
        Cursor cursor = null;
        try {
            db.beginTransaction();

            cursor = db.rawQuery(sql, new String[]{"1"});
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    CreativeVO creative = new CreativeVO();

                    String cid = cursor.getString(cursor.getColumnIndex("cid"));
                    if ("1".equals(cid)) {
                        db.delete("creative", "cid = ?", new String[]{cid});
                        continue;
                    }


                    creative.setCid(cursor.getString(cursor.getColumnIndex("cid")));
                    creative.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                    creative.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                    aList.add(creative);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.i(TAG, "queryAllLoaded error >>" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
            closeDatabase();
        }

        return aList;
    }


    Object object = new Object();

    /**
     * 更改数据库中素材的下载状态
     */
    public synchronized void updateStatusByCid(final String cid) {
        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                synchronized (object) {

                    SQLiteDatabase db = openDatabase();
                    if (db == null || TextUtils.isEmpty(cid)) {
                        return;
                    }

                    try {
                        db.beginTransaction();

                        Thread.sleep(10000);

                        String sql = "update creative set status = 1 where cid = ?";
                        db.execSQL(sql, new String[]{cid});

                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        e.fillInStackTrace();
                        Log.i(TAG, "updateStatusByCid error >>" + e.getMessage());
                    } finally {
                        db.endTransaction();
                        closeDatabase();
                    }


                }


            }
        });
    }


    /**
     * 根据cid，更新数据库中的playtime
     */
    public synchronized void updatePlaytimeByCid(final List<CreativeVO> creativeVOS) {
        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = openDatabase();
                if (db == null) {
                    return;
                }
                try {
                    db.beginTransaction();
                    String values = join(creativeVOS);
                    String updateSql = String.format(Locale.getDefault(),
                            "UPDATE %s SET %s=%s+1 WHERE %s in (%s)",
                            TABLE_NAME, PLAYTIME, PLAYTIME, CID, values);
                    db.execSQL(updateSql);
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.i(TAG, "updatePlaytimeByCid error >>" + e.getMessage());
                } finally {
                    db.endTransaction();
                    closeDatabase();
                }

            }
        });
    }


    /**
     * 根据cid，删除数据库中某个条目
     */
    public synchronized void deleteByCid(final String cid) {
        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = openDatabase();
                if (db == null || TextUtils.isEmpty(cid)) {
                    return;
                }

                try {
                    db.beginTransaction();
                    db.delete("creative", "cid = ?", new String[]{cid});
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.i(TAG, "deleteByCid error >>" + e.getMessage());
                } finally {
                    db.endTransaction();
                    closeDatabase();
                }
            }
        });
    }


    private void incrementRefByCid(final String cidStr) {
        ThreadPoolProxy.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = openDatabase();
                if (db == null) {
                    return;
                }

                try {
                    db.beginTransaction();
                    String updateSql = String.format(Locale.getDefault(),
                            "UPDATE %s SET %s=%s+1 WHERE %s in (%s)", TABLE_NAME, REF, REF, CID, cidStr);
                    db.execSQL(updateSql);
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.i(TAG, "incrementRefByCid error >>" + e.getMessage());
                } finally {
                    db.endTransaction();
                    closeDatabase();
                }

            }
        });
    }


    /**
     * 规定格式返回cid串
     *
     * @return 'a','b'
     */
    private static String join(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (String token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(",");
            }
            sb.append("'").append(token).append("'");
        }
        return sb.toString();
    }

    private static String join(List<CreativeVO> creativeVOS) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (CreativeVO creativeVO : creativeVOS) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(",");
            }
            sb.append("'").append(creativeVO.getCid()).append("'");
        }
        return sb.toString();
    }


}
