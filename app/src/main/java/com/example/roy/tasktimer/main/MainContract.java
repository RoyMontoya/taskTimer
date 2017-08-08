package com.example.roy.tasktimer.main;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.roy.tasktimer.base.BaseView;

/**
 * Created by Roy on 8/4/17.
 */

public class MainContract {

    interface View extends BaseView<Presenter> {

        void setupRecyclerView();

    }

    interface Presenter {

        void deleteTask(@NonNull Uri uri);

    }
}
