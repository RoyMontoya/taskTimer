package com.example.roy.tasktimer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment {

    public enum FragmentEditMode {EDIT, ADD}

    private FragmentEditMode mode;

    private EditText nameTextView;
    private EditText descriptionTextView;
    private EditText sortOrderTextView;
    private Button saveButton;
    private onSaveListener saveListener;

    interface onSaveListener {
        void onSaveClicked();
    }

    public AddEditActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        nameTextView = view.findViewById(R.id.addedit_name);
        descriptionTextView = view.findViewById(R.id.addedit_description);
        sortOrderTextView = view.findViewById(R.id.addedit_sortoder);
        saveButton = view.findViewById(R.id.addedit_save);

        Bundle args = getArguments();
        final Task task;
        if (args != null) {
            task = (Task) args.getSerializable(Task.class.getSimpleName());
            if (task != null) {
                nameTextView.setText(task.getName());
                descriptionTextView.setText(task.getDescription());
                sortOrderTextView.setText(String.valueOf(task.getSortOrder()));
                mode = FragmentEditMode.EDIT;
            } else {
                mode = FragmentEditMode.ADD;
            }
        } else {
            task = null;
            mode = FragmentEditMode.ADD;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sortOder;
                if (sortOrderTextView.length() > 0) {
                    sortOder = Integer.parseInt(sortOrderTextView.getText().toString());
                } else {
                    sortOder = 0;
                }

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues contentValues = new ContentValues();

                switch (mode) {
                    case EDIT:
                        if (!nameTextView.getText().toString().equals(task.getName()))
                            contentValues.put(TaskContract.Columns.TASK_NAME, nameTextView.getText().toString());
                        if (!descriptionTextView.getText().toString().equals(task.getDescription()))
                            contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, descriptionTextView.getText().toString());
                        if (sortOder != 0)
                            contentValues.put(TaskContract.Columns.TASK_SORTORDER, sortOder);
                        if (contentValues.size() != 0) {
                            contentResolver.update(TaskContract.buildTaskUri(task.getId()), contentValues, null, null);
                        }
                        break;
                    case ADD:
                        if (nameTextView.length() > 0) {
                            contentValues.put(TaskContract.Columns.TASK_NAME, nameTextView.getText().toString());
                            contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, descriptionTextView.getText().toString());
                            contentValues.put(TaskContract.Columns.TASK_SORTORDER, sortOder);
                            contentResolver.insert(TaskContract.CONTENT_URI, contentValues);
                        }
                        break;
                }
                if(saveListener != null) saveListener.onSaveClicked();
            }
        });

        return view;
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

    public boolean canClose(){
        return false;
    }

}