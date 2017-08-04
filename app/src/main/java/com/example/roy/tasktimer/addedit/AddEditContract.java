package com.example.roy.tasktimer.addedit;

import android.support.annotation.Nullable;

import com.example.roy.tasktimer.base.BaseView;
import com.example.roy.tasktimer.model.Task;

/**
 * Created by Roy on 8/3/17.
 */

public class AddEditContract {

    interface View extends BaseView<Presenter> {

        void initializeViews();

        void configureSaveButton();

        Task getTaskArgument();
    }

    interface Presenter {

        boolean canClose();

        void setCurrentTask(@Nullable Task task);

        void addEditCurrentTask(String name, String description, String sortOrder, int mode);

    }

}
