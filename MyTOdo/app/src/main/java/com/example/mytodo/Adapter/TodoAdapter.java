package com.example.mytodo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.MainActivity;
import com.example.mytodo.Model.ToDoModel;
import com.example.mytodo.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

        private List<ToDoModel> todoList;
        private MainActivity activity;

        public TodoAdapter (MainActivity activity){
            this.activity = activity;
        }

         public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout,parent,false);
            return new ViewHolder(itemView);
        }

        public void onBindViewHolder(ViewHolder holder, int position){
            ToDoModel item = todoList.get(position);
            holder.task.setText(item.getTask());
            holder.task.setChecked(toBoolean(item.getStatus()));
        }

        public  int getItemCount(){
            return todoList.size();
        }

        private boolean toBoolean(int n ){
            return n!=0;
        }

        public void setTasks(List<ToDoModel> todoList){
            this.todoList = todoList;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            CheckBox task;
            ViewHolder( View view){
                super(view);
                task = view.findViewById(R.id.todoCheckBox);
            }
        }


}
