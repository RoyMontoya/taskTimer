package com.example.roy.tasktimer.data;

import android.content.ContentResolver;
import android.content.Context;

import com.example.roy.tasktimer.AppModule;
import com.example.roy.tasktimer.data.db.AppDatabase;
import com.example.roy.tasktimer.util.DataScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/2/17.
 */
@Module(includes = AppModule.class)
public class DataModule {

    Context context;

    public DataModule(Context context){
        this.context = context;
    }

    @Provides
    @DataScope
    AppDatabase providesAppDatabase() {
        return AppDatabase.getIntance(context);
    }

    @Provides
    @DataScope
    ContentResolver providesContentResolver() {
        return context.getContentResolver();
    }

    @Provides
    @DataScope
    AppDataManager providesAppDataManager(ContentResolver contentResolver){
        return  new AppDataManager(contentResolver);
    }

}