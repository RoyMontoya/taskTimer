package com.example.roy.tasktimer.data;

import android.content.Context;

import com.example.roy.tasktimer.util.DataScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/2/17.
 */
@Module
public class DataModule {

    Context context;

    DataModule(Context context) {
        this.context = context;
    }

    @Provides
    @DataScope
    AppDatabase providesAppDatabase() {
        return AppDatabase.getIntance(context);
    }

}