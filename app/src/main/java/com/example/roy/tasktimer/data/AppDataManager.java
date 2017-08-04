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

    private final ContentResolver appProvider;

    AppDataManager(ContentResolver contentResolver) {
        this.appProvider = contentResolver;
    }


    @Override
    public void updateTaskInDataBase(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String where, @Nullable String[] selectionArgs) {
        appProvider.update(uri, contentValues, where, selectionArgs);
    }

    @Override
    public void insertIntoDatabase(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        appProvider.insert(uri, contentValues);
    }
}
