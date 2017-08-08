package com.example.roy.tasktimer.main;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.roy.tasktimer.data.DataManager;

import javax.inject.Inject;

/**
 * Created by Roy on 8/4/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private final DataManager dataManager;

    @Inject
    MainPresenter(MainContract.View fragment, DataManager dataManager) {
        this.view = fragment;
        this.dataManager = dataManager;
    }

    @Inject
    public void setPresenterIntoView() {
        view.setPresenter(this);
    }


    @Override
    public void deleteTask(@NonNull Uri uri) {
        dataManager.deleteTaskFromDatabase(uri);
    }
}
