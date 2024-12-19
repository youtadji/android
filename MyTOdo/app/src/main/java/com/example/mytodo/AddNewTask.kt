package com.example.mytodo

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.mytodo.Model.ToDoModel
import com.example.mytodo.Utils.DataBaseHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewTask : BottomSheetDialogFragment() {

    private var newTaskText: EditText? = null
    private var newTaskSaveButton: Button? = null
    private var db: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {  // Use override
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return inflater.inflate(R.layout.new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Non-nullable view
        super.onViewCreated(view, savedInstanceState)
        newTaskText = view.findViewById(R.id.newTaskText)
        newTaskSaveButton = view.findViewById(R.id.newTaskButton)

        db = DataBaseHandler(requireContext())
        db?.openDatabase()

        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            // That means we are updating a task
            isUpdate = true
            val task = bundle.getString("task", "")
            newTaskText?.setText(task)
            if (task.isNotEmpty()) {
                newTaskSaveButton?.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.pastel_blue)
                )
            }
        }

        newTaskText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (charSequence.isNullOrEmpty()) {
                    newTaskSaveButton?.isEnabled = false
                    newTaskSaveButton?.setTextColor(Color.GRAY)
                } else {
                    newTaskSaveButton?.isEnabled = true
                    newTaskSaveButton?.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.pastel_blue)
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val finalIsUpdate = isUpdate
        newTaskSaveButton?.setOnClickListener {
            val text = newTaskText?.text.toString()
            if (finalIsUpdate) {
                // update task
                val id = bundle?.getInt("id", -1) ?: -1
                db?.updateTask(id, text)
            } else {
                val task = ToDoModel()
                task.task = text
                task.status = 0
                db?.insertTask(task)
            }
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is DialogCloseListener) {
            activity.handleDialogClose(dialog)
        }
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        @JvmStatic
        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }
}
