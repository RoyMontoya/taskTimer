package com.example.roy.tasktimer.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/4/17.
 */
@Module
public class MainModule {

    private final MainContract.View view;

    public MainModule(MainContract.View fragment) {
        this.view = fragment;
    }

    @Provides
    MainContract.View providesMainView() {
        return view;
    }
}
