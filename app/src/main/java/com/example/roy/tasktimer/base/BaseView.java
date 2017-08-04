package com.example.roy.tasktimer.base;

import android.view.View;

/**
 * Created by Roy on 8/3/17.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void initFragment(View view);
}
