package com.example.roy.tasktimer;

import android.provider.BaseColumns;

/**
 * Created by Roy on 7/10/17.
 */

public class TaskContract {

    static final String TABLE_NAME = "Tasks";

    public static class Columns{

        public static final String _ID = BaseColumns._ID;
        public static final String TASK_NAME = "name";
        public static final String TASK_DESCRIPTION = "Description";
        public static final String TASK_SORTORDER = "SortOrder";

        private Columns(){}

    }



}
