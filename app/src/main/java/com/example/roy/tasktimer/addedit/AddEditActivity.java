package com.example.roy.tasktimer.addedit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.roy.tasktimer.R;
import com.example.roy.tasktimer.data.DataModule;
import com.example.roy.tasktimer.dialog.AppDialog;
import com.example.roy.tasktimer.listeners.DialogEventListener;
import com.example.roy.tasktimer.listeners.onSaveListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditActivity extends AppCompatActivity implements onSaveListener,
        DialogEventListener {

    public static final int DIALOG_ID_CANCEL_EDIT = 1;

    @Inject
    AddEditPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        initButterKnife();
        initToolbar();
        initDagger(getConfiguredFragment());
    }

    private void initButterKnife() {
        ButterKnife.bind(this);
    }

    private void initDagger(AddEditActivityFragment fragment) {
        DaggerAddEditComponent.builder()
                .dataModule(new DataModule(getApplicationContext()))
                .addEditModule(new AddEditModule(fragment))
                .build().inject(this);
    }

    @NonNull
    private AddEditActivityFragment getConfiguredFragment() {
        AddEditActivityFragment fragment = new AddEditActivityFragment();
        if (getIntent().getExtras() != null) {
            Bundle args = getIntent().getExtras();
            fragment.setArguments(args);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_add_edit, fragment);
        ft.commit();

        return fragment;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveClicked() {
        finish();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment)
                fm.findFragmentById(R.id.content_add_edit);
        if (fragment.canClose()) {
            super.onBackPressed();
        } else {
            showConfirmationDialog();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                AddEditActivityFragment fragment = (AddEditActivityFragment)
                        fm.findFragmentById(R.id.content_add_edit);
                if (fragment.canClose()) {
                    return super.onOptionsItemSelected(item);
                } else {
                    showConfirmationDialog();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Pending decision to change this method
    private void showConfirmationDialog() {
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL_EDIT);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDialog_message));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDialog_positive_caption);
        args.putInt(AppDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDialog_negative_caption);

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        finish();
    }

    @Override
    public void onDialogCancelled(int dialogId) {
    }

}