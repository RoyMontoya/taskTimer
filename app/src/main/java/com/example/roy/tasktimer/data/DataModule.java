package com.example.roy.tasktimer.data;

import android.content.ContentResolver;
import android.content.Context;

import com.example.roy.tasktimer.AppModule;
import com.example.roy.tasktimer.data.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/2/17.
 */
@Module(includes = AppModule.class)
public class DataModule {

    Context context;

    public DataModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase() {
        return AppDatabase.getIntance(context);
    }

    @Provides
    @Singleton
    ContentResolver providesContentResolver() {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    DataManager providesAppDataManager(ContentResolver contentResolver) {
        return new AppDataManager(contentResolver);
    }

}