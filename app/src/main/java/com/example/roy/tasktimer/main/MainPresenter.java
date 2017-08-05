package com.example.roy.tasktimer.main;

import javax.inject.Inject;

/**
 * Created by Roy on 8/4/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;

    @Inject
    MainPresenter(MainContract.View fragment) {
        this.view = fragment;
    }

    @Inject
    public void setPresenterIntoView() {
        view.setPresenter(this);
    }


}
