package com.edgar.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edgar.todolist.R
import com.edgar.todolist.data.model.MTask
import com.edgar.todolist.databinding.TaskItemBinding

// TaskAdapter.kt

class TaskAdapter(val listener: OnTaskClickListener) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var tasks: List<MTask> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TaskItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.task_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun setTasks(tasks: List<MTask>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: MTask) {
            binding.task = task
            binding.taskName.setOnLongClickListener { listener.onLongClick(task) }
            binding.taskCheck.setOnCheckedChangeListener { _, isChecked ->
                listener.onCheckClick(isChecked, binding.taskName, task.id)
            }
        }
    }
}

interface OnTaskClickListener {
    fun onCheckClick(isChecked: Boolean, tv: TextView, id: Long?)
    fun onLongClick(t: MTask): Boolean
}