package com.example.roy.tasktimer.addedit;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.example.roy.tasktimer.data.AppDataManager;
import com.example.roy.tasktimer.data.db.TaskContract;
import com.example.roy.tasktimer.model.Task;

/**
 * Created by Roy on 8/3/17.
 */

public class AddEditPresenter implements AddEditContract.Presenter {

    static final int EDIT = 0;
    static final int ADD = 1;

    private AppDataManager dataManager;
    private AddEditContract.View view;
    private ContentResolver contentResolver;
    private Task currentTask;

    AddEditPresenter(AppDataManager data, ContentResolver resolver, AddEditContract.View fragment){
        this.dataManager = data;
        this.view = fragment;
        this.contentResolver = resolver;
        view.setPresenter(this);
    }

    @Override
    public boolean canClose() {
        return false;
    }

    @Override
    public void setCurrentTask(Task task) {
        currentTask = task;
    }

    @Override
    public void addEditCurrentTask(String name, String description, String sortOrderString, int mode) {
        int sortOder = 0;
        if (sortOrderString.length() > 0) sortOder = Integer.parseInt(sortOrderString);

        ContentValues contentValues = new ContentValues();

        switch (mode) {
            case EDIT:
                if (!name.equals(currentTask.getName()))
                    contentValues.put(TaskContract.Columns.TASK_NAME, name);
                if (!description.equals(currentTask.getDescription()))
                    contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, description);
                if (sortOder != 0)
                    contentValues.put(TaskContract.Columns.TASK_SORTORDER, sortOder);
                if (contentValues.size() != 0) {
                    contentResolver.update(TaskContract.buildTaskUri(currentTask.getId()), contentValues, null, null);
                }
                break;
            case ADD:
                if (name.length() > 0) {
                    contentValues.put(TaskContract.Columns.TASK_NAME, name);
                    contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, description);
                    contentValues.put(TaskContract.Columns.TASK_SORTORDER, sortOder);
                    contentResolver.insert(TaskContract.CONTENT_URI, contentValues);
                }
                break;
        }
    }
}
