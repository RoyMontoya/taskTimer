package com.example.roy.tasktimer.addedit;

import com.example.roy.tasktimer.base.BaseView;

/**
 * Created by Roy on 8/3/17.
 */

public class AddEditContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter {

        boolean canClose();

    }

}
