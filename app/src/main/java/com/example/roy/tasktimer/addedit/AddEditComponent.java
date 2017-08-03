package com.example.roy.tasktimer.addedit;

import com.example.roy.tasktimer.AppModule;
import com.example.roy.tasktimer.data.DataModule;
import com.example.roy.tasktimer.util.DataScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roy on 8/3/17.
 */
@DataScope
@Singleton
@Component(modules = {DataModule.class, AddEditModule.class})
public interface AddEditComponent {

    void inject(AddEditActivityFragment fragment);

}
