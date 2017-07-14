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

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapater.OnTaskClickListener, AddEditActivityFragment.onSaveListener {

    private static final String TAG = "MainActivity";
    private boolean twoPane = false;
    private static final String ADD_EDIT_FRAGMENT = "AddEditFragment";

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
        getContentResolver().delete(TaskContract.buildTaskUri(task.getId()), null, null);
    }

    @Override
    public void onSaveClicked() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.task_details_container);
        if (fragment != null) {
           fm.beginTransaction().remove(fragment).commit();
        }
    }
}
