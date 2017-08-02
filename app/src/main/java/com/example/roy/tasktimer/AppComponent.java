package com.example.roy.tasktimer;

import dagger.Component;

/**
 * Created by Roy on 8/2/17.
 */
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);



}
