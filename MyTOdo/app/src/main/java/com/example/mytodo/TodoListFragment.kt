package com.example.mytodo

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.Adapter.TodoAdapter
import com.example.mytodo.Model.ToDoModel
import com.example.mytodo.Utils.DataBaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TodoListFragment : Fragment(), DialogCloseListener {
    private var tasksRecyclerView: RecyclerView? = null
    private var tasksAdapter: TodoAdapter? = null
    private var tasksList: List<ToDoModel> = listOf()
    private var db: DataBaseHandler? = null
    private var fab: FloatingActionButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DataBaseHandler(requireContext())
        db?.openDatabase()

        tasksList = db?.getAllTasks() ?: listOf()
        Collections.reverse(tasksList)

        tasksRecyclerView = view.findViewById(R.id.TasksRecyclerView)
        tasksRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        tasksAdapter = db?.let { TodoAdapter(it, requireActivity() as MainActivity) }
        tasksRecyclerView?.adapter = tasksAdapter

        fab = view.findViewById(R.id.FAB)

        val itemTouchHelper = ItemTouchHelper(RecylcerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab?.setOnClickListener {
            AddNewTask.newInstance().show(childFragmentManager, AddNewTask.TAG)
        }

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