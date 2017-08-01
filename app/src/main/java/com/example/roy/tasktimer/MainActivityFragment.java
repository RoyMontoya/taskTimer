package com.example.roy.tasktimer;

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

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorRecyclerViewAdapter adapater;
    private static final String TAG = "MainActivityFragment";
    public static final int LOADER_ID = 0;
    
    @BindView(R.id.task_list)
    RecyclerView recyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapater = new CursorRecyclerViewAdapter(null,
                (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity());
        recyclerView.setAdapter(adapater);
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
        adapater.swapCursor(data);
        int count = adapater.getItemCount();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapater.swapCursor(null);
    }
}
