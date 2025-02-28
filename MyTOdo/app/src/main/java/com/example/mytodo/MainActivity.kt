/*
package com.example.mytodo

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.Adapter.TodoAdapter
import com.example.mytodo.Model.ToDoModel
import com.example.mytodo.Utils.DataBaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity(), DialogCloseListener {

    private var tasksRecyclerView: RecyclerView? = null
    private var tasksAdapter: TodoAdapter? = null

    private var tasksList: List<ToDoModel> = listOf()
    private var db: DataBaseHandler? = null
    private var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) { // Use override
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DataBaseHandler(this)
        db?.openDatabase()

        tasksList = db?.getAllTasks() ?: listOf()
        Collections.reverse(tasksList)

        tasksRecyclerView = findViewById(R.id.TasksRecyclerView)
        tasksRecyclerView?.layoutManager = LinearLayoutManager(this)
        tasksAdapter = db?.let { TodoAdapter(it, this) }
        tasksRecyclerView?.adapter = tasksAdapter

        fab = findViewById(R.id.FAB)

        val itemTouchHelper = ItemTouchHelper(RecylcerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab?.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }

        // Submit the reversed list
        val reversedList = ArrayList(tasksList)
        Collections.reverse(reversedList)
        tasksAdapter?.submitList(reversedList)
    }

    override fun handleDialogClose(dialog: DialogInterface?) {
        tasksList = db?.getAllTasks() ?: listOf()
        Collections.reverse(tasksList)
        tasksAdapter?.submitList(ArrayList(tasksList))
    }
}
*/
package com.example.mytodo

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nav)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun handleDialogClose(dialog: DialogInterface?) {
        // Find the current fragment and forward the dialog close event
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager?.fragments?.get(0)
        if (currentFragment is DialogCloseListener) {
            currentFragment.handleDialogClose(dialog)
        }
    }

    fun navigateToDetail(taskText: String) {
        val action = TodoListFragmentDirections.actionListToDetail(taskText)
        navController.navigate(action)
    }
}