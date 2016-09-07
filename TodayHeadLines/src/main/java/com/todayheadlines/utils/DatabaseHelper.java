package com.todayheadlines.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HJM on 2016/9/7.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydata.db";
    private static final int version = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table mark(item varchar(30));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
