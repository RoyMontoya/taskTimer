package com.example.roy.tasktimer.addedit;

import com.example.roy.tasktimer.data.AppDataManager;
import com.example.roy.tasktimer.model.Task;

/**
 * Created by Roy on 8/3/17.
 */

public class AddEditPresenter implements AddEditContract.Presenter {

    private AppDataManager dataManager;
    private AddEditContract.View view;

    public AddEditPresenter(AppDataManager data, AddEditContract.View fragment){
        this.dataManager = data;
        this.view = fragment;
        view.setPresenter(this);
    }

    @Override
    public boolean canClose() {
        return false;
    }

    @Override
    public void setCurrentTask(Task task) {

    }

    @Override
    public void addEditCurrentTask(String name, String description, int sortOrder) {

    }
}
