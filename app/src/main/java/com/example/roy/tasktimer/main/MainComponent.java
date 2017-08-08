package com.example.roy.tasktimer.main;

import com.example.roy.tasktimer.data.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roy on 8/4/17.
 */
@Singleton
@Component(modules = {MainModule.class, DataModule.class})
public interface MainComponent {

    void inject(MainActivity activity);

}
