package com.example.roy.tasktimer.main;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roy.tasktimer.R;
import com.example.roy.tasktimer.adapter.CursorRecyclerViewAdapter;
import com.example.roy.tasktimer.data.db.TaskContract;
import com.example.roy.tasktimer.listeners.OnTaskClickListener;

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        MainContract.View {

    public static final int LOADER_ID = 0;
    private static final String TAG = "MainActivityFragment";

    @BindView(R.id.task_list)
    RecyclerView recyclerView;

    private CursorRecyclerViewAdapter adapter;

    private MainContract.Presenter presenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initFragment(view);
        return view;
    }

    @Override
    public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CursorRecyclerViewAdapter(null,
                (OnTaskClickListener) getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {TaskContract.Columns._ID, TaskContract.Columns.TASK_NAME,
                TaskContract.Columns.TASK_DESCRIPTION, TaskContract.Columns.TASK_SORTORDER};
        String sortOrder = TaskContract.Columns.TASK_SORTORDER + ", " +
                TaskContract.Columns.TASK_NAME + " COLLATE NOCASE";

        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(), TaskContract.CONTENT_URI, projection, null, null, sortOrder);
            default:
                throw new InvalidParameterException(TAG + "invalid Loader Id");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        int count = adapter.getItemCount();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void setPresenter(MainContract.Presenter mainPresenter) {
        presenter = mainPresenter;
    }

    @Override
    public void initFragment(View view) {
        ButterKnife.bind(this, view);
        setupRecyclerView();
    }
}