package com.example.mytodo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.Adapter.TodoAdapter;
import com.example.mytodo.Model.ToDoModel;
import com.example.mytodo.Utils.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecylcerView;
    private TodoAdapter tasksAdapter;

    private List<ToDoModel> tasksList;
    private DataBaseHandler db;
    private FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        db = new DataBaseHandler(this);
        db.openDatabase();

        tasksList = new ArrayList<>();

        tasksRecylcerView = findViewById(R.id.TasksRecyclerView);
        tasksRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TodoAdapter(db,this);
        tasksRecylcerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
        //ToDoModel task = new ToDoModel();
        //task.setTask("This is a Test Task");
        //task.setStatus(0);
        //task.setId(1);
        //tasksList.add(task);
        //tasksList.add(task);
        //tasksList.add(task);
        //tasksList.add(task);
        //tasksList.add(task);

        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
        //updating recycler view where we create that   set tasks function in the todoadapter
        //tasksAdapter.setTasks(tasksList);



    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
        tasksAdapter.notifyDataSetChanged(); //it wil uodat emy whole recyler view


    }
}