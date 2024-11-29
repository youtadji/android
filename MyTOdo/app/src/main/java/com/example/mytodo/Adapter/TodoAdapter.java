package com.example.mytodo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.AddNewTask;
import com.example.mytodo.MainActivity;
import com.example.mytodo.Model.ToDoModel;
import com.example.mytodo.R;
import com.example.mytodo.Utils.DataBaseHandler;

public class TodoAdapter extends ListAdapter<ToDoModel, TodoAdapter.ViewHolder> {

    private MainActivity activity;
    private DataBaseHandler db;

    public TodoAdapter(DataBaseHandler db, MainActivity activity) {
        super(DIFF_CALLBACK);
        this.db = db;
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<ToDoModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ToDoModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ToDoModel oldItem, @NonNull ToDoModel newItem) {
            // Compare unique IDs
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ToDoModel oldItem, @NonNull ToDoModel newItem) {
            // Compare the content
            return oldItem.getTask().equals(newItem.getTask()) && oldItem.getStatus() == newItem.getStatus();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = getItem(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public Context getContext() {
        return activity;
    }

    public void editItem(int position) {
        ToDoModel item = getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
