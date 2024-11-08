package com.example.mytodo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.Adapter.TodoAdapter;
import com.example.mytodo.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tasksRecylcerView;
    private TodoAdapter tasksAdapter;

    private List<ToDoModel> tasksList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        tasksList = new ArrayList<>();

        tasksRecylcerView = findViewById(R.id.TasksRecyclerView);
        tasksRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TodoAdapter(this);
        tasksRecylcerView.setAdapter(tasksAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("This is a Test Task");
        task.setStatus(0);
        task.setId(1);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);

        tasksAdapter.setTasks(tasksList);



    }
}