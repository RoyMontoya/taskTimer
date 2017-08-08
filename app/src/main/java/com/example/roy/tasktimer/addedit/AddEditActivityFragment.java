package com.example.roy.tasktimer.addedit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.roy.tasktimer.R;
import com.example.roy.tasktimer.listeners.onSaveListener;
import com.example.roy.tasktimer.model.Task;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment implements AddEditContract.View {

    @BindView(R.id.addedit_name)
    EditText nameTextView;
    @BindView(R.id.addedit_description)
    EditText descriptionTextView;
    @BindView(R.id.addedit_sortoder)
    EditText sortOrderTextView;
    @BindView(R.id.addedit_save)
    Button saveButton;

    AddEditContract.Presenter presenter;

    private int mode;
    private onSaveListener saveListener;

    public AddEditActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        initFragment(view);
        initializeViews();

        return view;
    }

    @Override
    public void configureSaveButton() {
        RxView.clicks(saveButton).subscribe(aVoid -> {

            presenter.addEditCurrentTask(nameTextView.getText().toString(),
                    descriptionTextView.getText().toString(),
                    sortOrderTextView.getText().toString(),
                    mode);

            if (saveListener != null) saveListener.onSaveClicked();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (!(activity instanceof onSaveListener)) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + "must implemente onSaveListener interface");
        }
        saveListener = (onSaveListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        saveListener = null;
    }

    @Override
    public void setPresenter(AddEditContract.Presenter addEditPresenter) {
        presenter = addEditPresenter;
    }

    @Override
    public void initFragment(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public Task getTaskArgument() {
        Bundle args = getArguments();
        Task task = null;
        if (args != null) task = (Task) args.getSerializable(Task.class.getSimpleName());

        return task;
    }

    @Override
    public void initializeViews() {
        Task task = getTaskArgument();
        mode = AddEditPresenter.ADD;

        if (task != null) {
            nameTextView.setText(task.getName());
            descriptionTextView.setText(task.getDescription());
            sortOrderTextView.setText(String.valueOf(task.getSortOrder()));
            mode = AddEditPresenter.EDIT;
        }
        presenter.setCurrentTask(task);
        configureSaveButton();
    }

    //Incomplete method this will check conditions to close the fragment later
    public boolean canClose() {
        return false;
    }

}