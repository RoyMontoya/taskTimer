package com.example.roy.tasktimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapater.OnTaskClickListener,
        AddEditActivityFragment.onSaveListener,
        AppDialog.DialogEvents {

    private static final String TAG = "MainActivity";
    private boolean twoPane = false;
    public static final int DIALOG_ID_DELETE = 1;
    public static final int DIALOG_ID_CANCEL_EDIT = 2;
    private static final String TASK_ID = "TaskId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.task_details_container) != null) twoPane = true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;
            case R.id.manumain_showDuration:

                break;
            case R.id.manumain_settings:

                break;
            case R.id.menumain_showAbout:

                break;
            case R.id.manumain_generate:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void taskEditRequest(Task task) {
        if (twoPane) {
            Log.d(TAG, "taskEditRequest: twopane");
            AddEditActivityFragment fragment = new AddEditActivityFragment();
            Bundle args = new Bundle();
            args.putSerializable(Task.class.getSimpleName(), task);
            fragment.setArguments(args);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.task_details_container, fragment);
            ft.commit();
        } else {
            Log.d(TAG, "taskEditRequest: onepane");
            Intent detailIntent = new Intent(this, AddEditActivity.class);
            if (task != null) detailIntent.putExtra(Task.class.getSimpleName(), task);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onEditClick(Task task) {
        taskEditRequest(task);
    }

    @Override
    public void onDeleteClick(Task task) {
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_DELETE);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.deldialog_message, task.getId(), task.getName()));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.deldialog_positive_caption);
        args.putLong(TASK_ID, task.getId());

        dialog.setArguments(args);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    public void onSaveClicked() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.task_details_container);
        if (fragment != null) {
            fm.beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                long taskId = args.getLong(TASK_ID);
                if (BuildConfig.DEBUG && taskId == 0) {
                    throw new AssertionError("TaskId is equal to zero!");
                }
                getContentResolver().delete(TaskContract.buildTaskUri(taskId), null, null);
                break;
            case DIALOG_ID_CANCEL_EDIT:

                break;
        }
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                break;
            case DIALOG_ID_CANCEL_EDIT:
                finish();
                break;
        }
    }

    @Override
    public void onDialogCancelled(int dialogId) {

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment)
                fm.findFragmentById(R.id.task_details_container);
        if (fragment == null || fragment.canClose()) {
            super.onBackPressed();
        } else {
            AppDialog dialog = new AppDialog();
            Bundle args = new Bundle();
            args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL_EDIT);
            args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDialog_message));
            args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDialog_positive_caption);
            args.putInt(AppDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDialog_negative_caption);

            dialog.setArguments(args);
            dialog.show(getFragmentManager(), null);

        }

    }
}
