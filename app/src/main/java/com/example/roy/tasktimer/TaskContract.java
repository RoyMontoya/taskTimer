package com.example.roy.tasktimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.roy.tasktimer.AppProvider.CONTENT_AUTHORITY;
import static com.example.roy.tasktimer.AppProvider.CONTENT_AUTHORITY_URI;

/**
 * Created by Roy on 7/10/17.
 */

public class TaskContract {

    static final String TABLE_NAME = "Tasks";


    public static class Columns {

        public static final String _ID = BaseColumns._ID;
        public static final String TASK_NAME = "name";
        public static final String TASK_DESCRIPTION = "Description";
        public static final String TASK_SORTORDER = "SortOrder";

        private Columns() {
        }

    }

    /*
           Uri to access the tasks table
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    public static long getTaskId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
