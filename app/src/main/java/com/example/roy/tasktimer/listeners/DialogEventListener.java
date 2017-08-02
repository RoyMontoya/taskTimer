package com.example.roy.tasktimer.listeners;

import android.os.Bundle;

/**
 * Created by Roy on 8/1/17.
 */

public interface DialogEventListener {

    void onPositiveDialogResult(int dialogId, Bundle args);

    void onNegativeDialogResult(int dialogId, Bundle args);

    void onDialogCancelled(int dialogId);

}
