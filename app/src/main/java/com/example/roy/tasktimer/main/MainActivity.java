package com.example.roy.tasktimer.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.roy.tasktimer.BuildConfig;
import com.example.roy.tasktimer.R;
import com.example.roy.tasktimer.addedit.AddEditActivity;
import com.example.roy.tasktimer.addedit.AddEditActivityFragment;
import com.example.roy.tasktimer.data.db.TaskContract;
import com.example.roy.tasktimer.dialog.AppDialog;
import com.example.roy.tasktimer.listeners.DialogEventListener;
import com.example.roy.tasktimer.listeners.OnTaskClickListener;
import com.example.roy.tasktimer.listeners.onSaveListener;
import com.example.roy.tasktimer.model.Task;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener,
        onSaveListener,
        DialogEventListener {

    public static final int DIALOG_ID_DELETE = 1;
    public static final int DIALOG_ID_CANCEL_EDIT = 2;
    private static final String TAG = "MainActivity";
    private static final String TASK_ID = "TaskId";
    private static final String VERSION_CODE = "v" + BuildConfig.VERSION_NAME;

    private boolean twoPane = false;
    private AlertDialog dialog = null;

    @Inject
    MainPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivityComponents();
    }

    private void initActivityComponents() {
        initDagger();
        initButterKnife();
        initToolbar();
        checkTwoPaneConfig();
    }

    private void initButterKnife() {
        ButterKnife.bind(this);
    }

    private void initDagger() {
        MainActivityFragment fragment = (MainActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            DaggerMainComponent.builder()
                    .mainModule(new MainModule(fragment))
                    .build().inject(this);
        }
    }

    private void checkTwoPaneConfig() {
        if (findViewById(R.id.task_details_container) != null) twoPane = true;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;
            case R.id.manumain_showDuration:

                break;
            case R.id.manumain_settings:

                break;
            case R.id.menumain_showAbout:
                showAboutDialog();
                break;
            case R.id.manumain_generate:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void showAboutDialog() {
        @SuppressLint("InflateParams")
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
        dialog = buildAboutDialog(messageView);
        configureAboutVersion(messageView);
        configureSupportWebLink(messageView);
        dialog.setCanceledOnTouchOutside(true);


        dialog.show();
    }

    private void configureAboutVersion(View messageView) {
        TextView textView = messageView.findViewById(R.id.about_version);
        textView.setText(VERSION_CODE);
    }

    private void configureSupportWebLink(View messageView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            final TextView weblink = messageView.findViewById(R.id.about_clickable_link);
            RxView.clicks(weblink).subscribe(aVoid -> {
                String url = weblink.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            });
        }
    }

    public AlertDialog buildAboutDialog(View messageView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_clock);
        builder.setView(messageView);
        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            if (dialog != null && dialog.isShowing()) dialog.dismiss();
        });

        return builder.create();
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
        dialog.show(getSupportFragmentManager(), null);
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
                //// TODO: 8/7/17 convert to MVP
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
    protected void onStop() {
        super.onStop();
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment)
                fm.findFragmentById(R.id.task_details_container);
        if (fragment == null || fragment.canClose()) {
            super.onBackPressed();
        } else {
            showUnSavedChangesDialog();

        }

    }

    private void showUnSavedChangesDialog() {
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL_EDIT);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDialog_message));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDialog_positive_caption);
        args.putInt(AppDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDialog_negative_caption);

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), null);
    }
}
