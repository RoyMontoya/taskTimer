package com.example.roy.tasktimer.main;

import com.example.roy.tasktimer.util.DataScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roy on 8/4/17.
 */
@DataScope
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivityFragment fragment);

}
