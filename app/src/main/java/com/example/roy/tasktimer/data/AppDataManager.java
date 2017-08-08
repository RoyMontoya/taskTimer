package com.example.roy.tasktimer.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Roy on 8/2/17.
 */

public class AppDataManager implements DataManager {

    private final ContentResolver AppContentResolver;

    AppDataManager(ContentResolver contentResolver) {
        this.AppContentResolver = contentResolver;
    }


    @Override
    public void updateTaskInDataBase(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        AppContentResolver.update(uri, contentValues, null, null);
    }

    @Override
    public void insertTaskIntoDatabase(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        AppContentResolver.insert(uri, contentValues);
    }

    @Override
    public void deleteTaskFromDatabase(@NonNull Uri uri) {
        AppContentResolver.delete(uri, null, null);
    }
}
