package com.example.roy.tasktimer;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/2/17.
 */
@Module
public class AppModule {

    Application application;

    AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

}