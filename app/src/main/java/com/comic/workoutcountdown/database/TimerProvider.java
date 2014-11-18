package com.comic.workoutcountdown.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by zjy on 11/18/14.
 */
public class TimerProvider extends ContentProvider {

    private static final String DB_NAME = "workoutcountdown.db";
    private static final int DB_VERSION = 1;
    public static final String DB_AUTHOR = "com.comic.workoutcountdown";
    private MySQLiteOpenHelper mOpenHelper;

    public static final Uri CONTENT_URI_TIMER = Uri.parse("content://com.comic.workoutcountdown/timer");

    // Name of table in the database
    private static final String DB_TABLE_TIMER = "timer";

    // main table topic keys
    public static final String KEY_TIMER_ID = "_id";
    public static final String KEY_TIMER_WORKOUT_NAME = "name";
    public static final String KEY_TIMER_WORKOUT_TIME = "workout";
    public static final String KEY_TIMER_REST_TIME = "rest";
    public static final String KEY_TIMER_PREPARE_TIME = "prepare";
    public static final String KEY_TIMER_SET = "set";
    public static final String KEY_TIMER_REPET = "repet";
    public static final String KEY_TIMER_CREATE_DATE = "create";

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(getTable(uri));

        String orderBy;
        if (uri.equals(CONTENT_URI_TIMER) && TextUtils.isEmpty(sortOrder)) {
            orderBy = KEY_TIMER_CREATE_DATE;
        } else {
            orderBy = sortOrder;
        }

        Cursor c = null;

        try {
            c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
            c.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (SQLiteDiskIOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String table_name = getTable(uri);

        try {
            db.insert(table_name, KEY_TIMER_WORKOUT_TIME, values);
        } catch (SQLiteDiskIOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String table_name = getTable(uri);

        int count = 0;
        try {
            count = db.delete(table_name, selection, selectionArgs);
        } catch (SQLiteDiskIOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String table_name = getTable(uri);

        long count = 0;
        try {
            count = db.update(table_name, values, selection, selectionArgs);
            if (count <= 0) {
                count = db.insert(table_name, KEY_TIMER_WORKOUT_TIME, values);
            }
        } catch (SQLiteDiskIOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (int) count;
    }

    private String getTable(Uri uri) {
        String sUri = uri.toString();
        if (sUri.equals(CONTENT_URI_TIMER.toString())) {
            return DB_TABLE_TIMER;
        }
        return "";
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.comic.workoutcountdown";
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTableTimer(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        private void createTableTimer(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_TIMER + " ("//
                    + KEY_TIMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"//
                    + KEY_TIMER_WORKOUT_NAME + " TEXT NOT NULL DEFAULT '',"//
                    + KEY_TIMER_WORKOUT_TIME + " INTEGER NOT NULL DEFAULT 0,"//
                    + KEY_TIMER_REST_TIME + " INTEGER NOT NULL DEFAULT 0,"//
                    + KEY_TIMER_PREPARE_TIME + " INTEGER NOT NULL DEFAULT 0,"//
                    + KEY_TIMER_SET + " INTEGER NOT NULL DEFAULT 0,"//
                    + KEY_TIMER_REPET + " INTEGER NOT NULL DEFAULT 0,"//
                    + KEY_TIMER_CREATE_DATE + " INTEGER NOT NULL DEFAULT 0"//
                    + ");";
            db.execSQL(sql);

        }
    }
}
