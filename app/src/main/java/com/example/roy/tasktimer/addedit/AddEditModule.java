package com.example.roy.tasktimer.addedit;

import com.example.roy.tasktimer.data.AppDataManager;
import com.example.roy.tasktimer.util.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 8/3/17.
 */
@Module
class AddEditModule {

    private AddEditContract.View view;

    AddEditModule(AddEditContract.View fragment){
        view = fragment;
    }

    @Provides
    @PerActivity
    AddEditContract.View providesAddEditView(){
        return view;
    }

}
