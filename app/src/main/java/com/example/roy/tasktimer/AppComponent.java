package com.example.roy.tasktimer;

import com.example.roy.tasktimer.data.DataModule;

import dagger.Component;

/**
 * Created by Roy on 8/2/17.
 */
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    void inject(App app);

}
