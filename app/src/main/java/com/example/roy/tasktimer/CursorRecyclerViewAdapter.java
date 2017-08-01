package com.example.roy.tasktimer;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Roy on 7/12/17.
 */

class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.TaskViewHolder> {
    private static final String TAG = "CursorRecyclerViewAdapa";
    private Cursor cursor;
    private OnTaskClickListener listener;

    public CursorRecyclerViewAdapter(Cursor cursor, OnTaskClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_row, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (cursor == null || cursor.getCount() == 0) {
            holder.name.setText(R.string.default_row_name);
            holder.description.setText(R.string.default_description_row);
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!cursor.moveToPosition(position)) {
                throw new IllegalStateException("couldnt move cursor to position: " + position);
            }
            final Task task = new Task(cursor.getLong(cursor.getColumnIndex(TaskContract.Columns._ID)),
                    cursor.getString(cursor.getColumnIndex(TaskContract.Columns.TASK_NAME)),
                    cursor.getString(cursor.getColumnIndex(TaskContract.Columns.TASK_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(TaskContract.Columns.TASK_SORTORDER)));
            holder.name.setText(task.getName());
            holder.description.setText(task.getDescription());
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);

            RxView.clicks(holder.editButton).subscribe(aVoid -> {
                if (listener != null) listener.onEditClick(task);
            });
            RxView.clicks(holder.deleteButton).subscribe(aVoid -> {
                if (listener != null) listener.onDeleteClick(task);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null || cursor.getCount() == 0) {
            return 1;
        }
        return cursor.getCount();
    }

    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == cursor) return null;

        final Cursor oldCursor = cursor;
        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    interface OnTaskClickListener {
        void onEditClick(Task task);

        void onDeleteClick(Task task);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tli_name)
        TextView name;
        @BindView(R.id.tli_description)
        TextView description;
        @BindView(R.id.tli_edit)
        ImageButton editButton;
        @BindView(R.id.tli_delete)
        ImageButton deleteButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
