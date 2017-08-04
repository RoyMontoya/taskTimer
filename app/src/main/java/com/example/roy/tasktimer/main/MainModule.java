package com.example.roy.tasktimer.main;

import com.example.roy.tasktimer.util.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/4/17.
 */
@Module
public class MainModule {

    private MainContract.View view;

    MainModule(MainContract.View fragment) {
        this.view = fragment;
    }

    @Provides
    @PerActivity
    MainContract.View providesMainView() {
        return view;
    }
}
