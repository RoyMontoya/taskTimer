package com.example.roy.tasktimer.data;

import android.content.ContentResolver;
import android.content.Context;

import javax.inject.Inject;

/**
 * Created by Roy on 8/2/17.
 */

public class AppDataManager implements DataManager {

    private final ContentResolver appProvider;

    public AppDataManager(ContentResolver contentResolver) {
        this.appProvider = contentResolver;
    }


}
