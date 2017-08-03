package com.example.roy.tasktimer.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Roy on 7/10/17.
 */

public class AppDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TaskTimer.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = "AppDatabase";
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static AppDatabase getIntance(Context context) {
        if (instance == null) {
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String taskSQL = "CREATE TABLE " + TaskContract.TABLE_NAME + " ("
                + TaskContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + TaskContract.Columns.TASK_NAME + " TEXT NOT NULL, "
                + TaskContract.Columns.TASK_DESCRIPTION + " TEXT, "
                + TaskContract.Columns.TASK_SORTORDER + " INTEGER);";

        sqLiteDatabase.execSQL(taskSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch (i) {
            case 1:

                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + i1);
        }
    }
}
