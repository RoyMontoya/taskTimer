package com.example.roy.tasktimer.data;

import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Roy on 8/2/17.
 */

public interface DataManager {

    void updateTaskInDataBase(@NonNull Uri uri, @Nullable ContentValues contentValues);

    void insertTaskIntoDatabase(@NonNull Uri uri, @Nullable ContentValues contentValues);

    void deleteTaskFromDatabase(@NonNull Uri uri);
}
