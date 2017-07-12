package com.example.roy.tasktimer;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Roy on 7/12/17.
 */

class CursorRecyclerViewAdapater extends RecyclerView.Adapter<CursorRecyclerViewAdapater.TaskViewHolder> {
    private Cursor cursor;

    public CursorRecyclerViewAdapater(Cursor cursor) {
        this.cursor = cursor;
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
            holder.name.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Columns.TASK_NAME)));
            holder.description.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Columns.TASK_DESCRIPTION)));
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
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

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageButton editButton;
        ImageButton deleteButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tli_name);
            this.description = itemView.findViewById(R.id.tli_description);
            this.editButton = itemView.findViewById(R.id.tli_edit);
            this.deleteButton = itemView.findViewById(R.id.tli_delete);
        }
    }
}
