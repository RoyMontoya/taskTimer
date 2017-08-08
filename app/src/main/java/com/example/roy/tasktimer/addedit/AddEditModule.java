package com.example.roy.tasktimer.addedit;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/3/17.
 */
@Module
class AddEditModule {

    private final AddEditContract.View view;

    AddEditModule(AddEditContract.View fragment) {
        view = fragment;
    }

    @Provides
    AddEditContract.View providesAddEditView() {
        return view;
    }

}