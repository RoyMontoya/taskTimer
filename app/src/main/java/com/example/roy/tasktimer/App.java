package com.example.roy.tasktimer;

import android.app.Application;

/**
 * Created by Roy on 8/2/17.
 */

public class App extends Application {

    static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent =DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);

    }

    public static AppComponent getApplication() {
        return appComponent;
    }

}
