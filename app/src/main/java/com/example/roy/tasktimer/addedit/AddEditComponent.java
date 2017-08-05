package com.example.roy.tasktimer.addedit;

import com.example.roy.tasktimer.data.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roy on 8/3/17.
 */
@Singleton
@Component(modules = {DataModule.class, AddEditModule.class})
public interface AddEditComponent {

    void inject(AddEditActivity activity);

}
