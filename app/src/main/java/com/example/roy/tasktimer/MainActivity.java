package com.example.roy.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] projection = {TaskContract.Columns._ID,
                                TaskContract.Columns.TASK_NAME,
                                TaskContract.Columns.TASK_DESCRIPTION,
                                TaskContract.Columns.TASK_SORTORDER};

        ContentResolver contentResolver = getContentResolver();

//        ContentValues values = new ContentValues();
//        values.put(TaskContract.Columns.TASK_SORTORDER, "99");
//        values.put(TaskContract.Columns.TASK_DESCRIPTION, "completed!");
//        String selection = TaskContract.Columns.TASK_SORTORDER + " = " + 2;
//
//        int count = contentResolver.delete(TaskContract.buildTaskUri(3), null, null);



//        values.put(TaskContract.Columns.TASK_NAME, "Content provider");
//        values.put(TaskContract.Columns.TASK_DESCRIPTION, "provider description");
//        int count = contentResolver.update(TaskContract.buildTaskUri(4), values, null, null);

//        values.put(TaskContract.Columns.TASK_NAME, "new Task1");
//        values.put(TaskContract.Columns.TASK_DESCRIPTION, "test task");
//        values.put(TaskContract.Columns.TASK_SORTORDER, 2);

//        Uri uri = contentResolver.insert(TaskContract.CONTENT_URI, values);



        Cursor cursor = contentResolver.query(TaskContract.CONTENT_URI,
                projection,
                null,
                null,
                TaskContract.Columns.TASK_SORTORDER);

        if (cursor != null){
            Log.d(TAG, "onCreate: "+ cursor.getCount());
            while (cursor.moveToNext()){
                for (int i = 0; i < cursor.getColumnCount(); i++){
                    Log.d(TAG, "onCreate: "+ cursor.getColumnName(i)+ ":" + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ====================");
            }
            cursor.close();
        }

//        AppDatabase appDatabase = AppDatabase.getIntance(this);
//        final SQLiteDatabase db = appDatabase.getReadableDatabase();

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (id == R.id.manumain_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
