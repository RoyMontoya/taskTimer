package com.example.roy.tasktimer.main;

import com.example.roy.tasktimer.util.PerActivity;

import dagger.Component;

/**
 * Created by Roy on 8/4/17.
 */
@PerActivity
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

}
