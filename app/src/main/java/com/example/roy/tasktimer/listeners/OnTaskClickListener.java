package com.example.roy.tasktimer.listeners;

import com.example.roy.tasktimer.model.Task;

/**
 * Created by Roy on 8/1/17.
 */

public interface OnTaskClickListener {

    void onEditClick(Task task);

    void onDeleteClick(Task task);

}
