package com.cloudtech.antony.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huangdong on 17/12/5.
 * antony.huang@yeahmobi.com
 */
public class CreativeSqliteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "creative";
    public static final String CID = "cid";
    public static final String URL = "url";
    public static final String TYPE = "type";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String PATH = "path";
    public static final String PLAYTIME = "playtime";
    public static final String STATUS = "status";
    public static final String REF = "ref";

    private static final int CURRENT_VERSION = 2;

    public CreativeSqliteOpenHelper(Context context) {
        super(context, "RewardedVideo.db", null, CURRENT_VERSION);
    }

    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + "("
            + CID + " TEXT PRIMARY KEY, "
            + URL + " TEXT, "
            + TYPE + " TEXT, "
            + WIDTH + " integer, "
            + HEIGHT + " integer, "
            + PATH + " TEXT, "
            + PLAYTIME + " integer, "
            + STATUS + " integer, "
            + REF + " integer DEFAULT 1);";

    private static final String TABLE_RENAME = "old_creative";

    private static final String RENAME_SQL = "alter table "
            + TABLE_NAME
            + " rename to "
            + TABLE_RENAME;

    private static final String ADD_SQL = "alter table "
            + TABLE_RENAME
            + " add column "
            + REF
            + " integer DEFAULT 1;";

    private static final String INSERT_SQL = "insert into "
            + TABLE_NAME
            + " select * from "
            + TABLE_RENAME;

    private static final String DROP_SQL = "drop table "
            + TABLE_RENAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == CURRENT_VERSION) {
            db.beginTransaction();
            db.execSQL(RENAME_SQL);
            db.execSQL(ADD_SQL);
            db.execSQL(CREATE_SQL);
            db.execSQL(INSERT_SQL);
            db.execSQL(DROP_SQL);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }


}
