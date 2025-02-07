package com.example.mytodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TodoDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskText = arguments?.getString("task_text")
        view.findViewById<TextView>(R.id.taskDetailText).text = taskText
    }

    companion object {
        fun newInstance(taskText: String): TodoDetailFragment {
            val fragment = TodoDetailFragment()
            val args = Bundle()
            args.putString("task_text", taskText)
            fragment.arguments = args
            return fragment
        }
    }
}