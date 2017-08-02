package com.example.roy.tasktimer.data;

import com.example.roy.tasktimer.util.DataScope;

import dagger.Component;

/**
 * Created by Roy on 8/2/17.
 */
@DataScope
@Component(modules = DataModule.class)
public interface DataComponent {

    void inject(AppProvider provider);

}
