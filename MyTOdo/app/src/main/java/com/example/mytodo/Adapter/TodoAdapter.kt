/*
package com.example.mytodo.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.AddNewTask
import com.example.mytodo.MainActivity
import com.example.mytodo.Model.ToDoModel
import com.example.mytodo.R
import com.example.mytodo.Utils.DataBaseHandler

class TodoAdapter(private val db: DataBaseHandler, private val activity: MainActivity) :
    ListAdapter<ToDoModel, TodoAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ToDoModel>() {
            override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.task == newItem.task && oldItem.status == newItem.status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item = getItem(position)
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)

        holder.task.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            val newStatus = if (b) 1 else 0
            db.updateStatus(item.id, newStatus)
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    val context: Context
        get() = activity

    fun deleteItem(position: Int) {
        val item = getItem(position)
        db.deleteTask(item.id)
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }

    fun editItem(position: Int) {
        val item = getItem(position)
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("task", item.task)
        val fragment = AddNewTask()
        fragment.arguments = bundle
        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}

*/
package com.example.mytodo.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.AddNewTask
import com.example.mytodo.MainActivity
import com.example.mytodo.Model.ToDoModel
import com.example.mytodo.R
import com.example.mytodo.Utils.DataBaseHandler

class TodoAdapter(private val db: DataBaseHandler, private val activity: MainActivity) :
    ListAdapter<ToDoModel, TodoAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ToDoModel>() {
            override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.task == newItem.task && oldItem.status == newItem.status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item = getItem(position)
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)

        holder.task.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            val newStatus = if (b) 1 else 0
            db.updateStatus(item.id, newStatus)
        }

        // Add click listener for the whole item view
        holder.itemView.setOnClickListener {
            activity.navigateToDetail(item.task)
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    val context: Context
        get() = activity

    fun deleteItem(position: Int) {
        val item = getItem(position)
        db.deleteTask(item.id)
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }

    fun editItem(position: Int) {
        val item = getItem(position)
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("task", item.task)
        val fragment = AddNewTask()
        fragment.arguments = bundle
        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}