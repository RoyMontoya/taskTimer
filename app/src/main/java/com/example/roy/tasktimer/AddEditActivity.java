package com.example.roy.tasktimer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AddEditActivity extends AppCompatActivity implements AddEditActivityFragment.onSaveListener {
    private static final String TAG = "AddEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras() != null){
            AddEditActivityFragment fragment = new AddEditActivityFragment();
            Bundle args = getIntent().getExtras();
            fragment.setArguments(args);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.content_add_edit, fragment);
            ft.commit();
        }
    }

    @Override
    public void onSaveClicked() {
        finish();
    }
}
