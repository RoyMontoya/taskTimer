package com.example.roy.tasktimer.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/4/17.
 */
@Module
class MainModule {

    private final MainContract.View view;

    MainModule(MainContract.View fragment) {
        this.view = fragment;
    }

    @Provides
    MainContract.View providesMainView() {
        return view;

    }
}
