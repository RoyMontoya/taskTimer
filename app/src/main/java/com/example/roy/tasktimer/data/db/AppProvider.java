package com.example.roy.tasktimer.data.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Roy on 7/10/17.
 */

public class AppProvider extends ContentProvider {

    static final String CONTENT_AUTHORITY = "com.example.roy.tasktimer";
    public final static Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final int TASKS = 100;
    private static final int TASKS_ID = 101;
    public static final UriMatcher uriMatcher = buildUriMatcher();
    private static final int TIMINGS = 200;
    private static final int TIMINGS_ID = 201;
    private static final int TASKS_DURATION = 400;

    //    private static final int TASKS_TIMINGS = 300;
//    private static final int TASKS_TIMINGS_ID = 301;
    private static final int TASKS_DURATION_ID = 401;

    private AppDatabase openHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, TaskContract.TABLE_NAME, TASKS);
        matcher.addURI(CONTENT_AUTHORITY, TaskContract.TABLE_NAME + "/#", TASKS_ID);

//        matcher.addURI(CONTENT_AUTHORITY, TimmingsContract.TABLE_NAME + "/#", TIMINGS);
//        matcher.addURI(CONTENT_AUTHORITY, TimmingsContract.TABLE_NAME + "/#", TIMINGS_ID);
//
//        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME + "/#", TASKS_DURATION);
//        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME + "/#", TASKS_DURATION_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = AppDatabase.getIntance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int matcher = uriMatcher.match(uri);

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        switch (matcher) {
            case TASKS:
                sqLiteQueryBuilder.setTables(TaskContract.TABLE_NAME);
                break;
            case TASKS_ID:
                sqLiteQueryBuilder.setTables(TaskContract.TABLE_NAME);
                long taskId = TaskContract.getTaskId(uri);
                sqLiteQueryBuilder.appendWhere(TaskContract.Columns._ID + "=" + taskId);
                break;
//            case TIMINGS:
//                sqLiteQueryBuilder.setTables(TimingsContract.TABLE_NAME);
//                break;
//            case TIMINGS_ID:
//                sqLiteQueryBuilder.setTables(TimingsContract.TABLE_NAME);
//                long timingId = TimingsContract.getTimingsId(uri);
//                sqLiteQueryBuilder.appendWhere(TimingsContract.Columns._ID + "=" + timingId);
//                break;
//            case TASKS_DURATION:
//                sqLiteQueryBuilder.setTables(DurationsContract.TABLE_NAME);
//                break;
//            case TASKS_DURATION_ID:
//                sqLiteQueryBuilder.setTables(DurationsContract.TABLE_NAME);
//                long durationId = DurationsContract.getTimingsId(uri);
//                sqLiteQueryBuilder.appendWhere(DurationsContract.Columns._ID + "=" + durationId);
//                break;
            default:
                throw new IllegalStateException("Unknown uri:" + uri);
        }
        SQLiteDatabase database = openHelper.getReadableDatabase();
//        return sqLiteQueryBuilder.query(database, strings, s, strings1, null, null, s1);
        Cursor cursor = sqLiteQueryBuilder.query(database, strings, s, strings1, null, null, s1);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case TASKS:
                return TaskContract.CONTENT_TYPE;
            case TASKS_ID:
                return TaskContract.CONTENT_ITEM_TYPE;
//            case TIMINGS:
//                return TaskContract.CONTENT_TYPE;
//            case TIMINGS_ID:
//                return TaskContract.CONTENT_ITEM_TYPE;
//            case TASKS_DURATION:
//                return TaskContract.CONTENT_TYPE;
//            case TASKS_DURATION_ID:
//                return TaskContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown uri:" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert: called with uri: " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "match is:" + match);

        final SQLiteDatabase database;
        Uri resultUri;
        long recordId;
        switch (match) {
            case TASKS:
                database = openHelper.getWritableDatabase();
                recordId = database.insert(TaskContract.TABLE_NAME, null, contentValues);
                if (recordId >= 0) {
                    resultUri = TaskContract.buildTaskUri(recordId);
                } else {
                    throw new android.database.sqlite.SQLiteException("Failed to insert into " + uri.toString());
                }
                break;
            case TIMINGS:
//                database = openHelper.getWritableDatabase();
//                recordId = database.insert(TimingsContract.Timings.buildTimingUri(recordId));
//                if (recordId >= 0) {
//                    resultUri = TimingsContract.Timings.buildTimingUri(recordId);
//                } else {
//                    throw new android.database.sqlite.SQLiteException("Failed to insert into " + uri.toString());
//                }
//                break;
            default:
                throw new IllegalStateException("Unknown uri:" + uri);
        }
        if (recordId > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "update: called with" + uri);
        final int match = uriMatcher.match(uri);

        final SQLiteDatabase database;
        int count;

        String selectionCriteria;
        switch (match) {
            case TASKS:
                database = openHelper.getWritableDatabase();
                count = database.delete(TaskContract.TABLE_NAME, s, strings);
                break;
            case TASKS_ID:
                database = openHelper.getWritableDatabase();
                long taskId = TaskContract.getTaskId(uri);
                selectionCriteria = TaskContract.Columns._ID + " = " + taskId;
                if (s != null && s.length() > 0) {
                    selectionCriteria += " AND (" + s + ")";
                }
                count = database.delete(TaskContract.TABLE_NAME, selectionCriteria, strings);
                break;
//            case TIMINGS:
//                database = openHelper.getWritableDatabase();
//                count = database.delete(TimingsContract.TABLE_NAME, s, strings);
//                break;
//            case TIMINGS_ID:
//                database = openHelper.getWritableDatabase();
//                long timingsId = TimingsContract.getTaskId(uri);
//                selectionCriteria = TimingsContract.Columns._ID + " = " + timingsId;
//                if(s != null && s.length() >0){
//                    selectionCriteria += " AND (" + s + ")";
//                }
//                count = database.delete(TimingsContract.TABLE_NAME, selectionCriteria, strings);
//                break;
            default:
                throw new IllegalStateException("Unknown uri:" + uri);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "update: called with" + uri);
        final int match = uriMatcher.match(uri);

        final SQLiteDatabase database;
        int count;

        String selectionCriteria;
        switch (match) {
            case TASKS:
                database = openHelper.getWritableDatabase();
                count = database.update(TaskContract.TABLE_NAME, contentValues, s, strings);
                break;
            case TASKS_ID:
                database = openHelper.getWritableDatabase();
                long taskId = TaskContract.getTaskId(uri);
                selectionCriteria = TaskContract.Columns._ID + " = " + taskId;
                if (s != null && s.length() > 0) {
                    selectionCriteria += " AND (" + s + ")";
                }
                count = database.update(TaskContract.TABLE_NAME, contentValues, selectionCriteria, strings);
                break;
//            case TIMINGS:
//                database = openHelper.getWritableDatabase();
//                count = database.update(TimingsContract.TABLE_NAME, contentValues, s, strings);
//                break;
//            case TIMINGS_ID:
//                database = openHelper.getWritableDatabase();
//                long timingsId = TimingsContract.getTaskId(uri);
//                selectionCriteria = TimingsContract.Columns._ID + " = " + timingsId;
//                if(s != null && s.length() >0){
//                    selectionCriteria += " AND (" + s + ")";
//                }
//                count = database.update(TimingsContract.TABLE_NAME, contentValues, selectionCriteria, strings);
//                break;
            default:
                throw new IllegalStateException("Unknown uri:" + uri);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }
}
