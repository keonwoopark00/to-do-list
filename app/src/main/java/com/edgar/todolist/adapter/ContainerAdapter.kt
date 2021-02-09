package com.edgar.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edgar.todolist.R
import com.edgar.todolist.data.model.TaskContainer
import com.edgar.todolist.databinding.MainListItemBinding

// ContainerAdapter.kt

class ContainerAdapter(val listener: OnContainerClickListener) :
    RecyclerView.Adapter<ContainerAdapter.ViewHolder>() {

    private var containers: List<TaskContainer> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: MainListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(containers[position])
    }

    override fun getItemCount(): Int {
        return containers.size
    }

    fun setContainers(containers: List<TaskContainer>) {
        this.containers = containers
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MainListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(container: TaskContainer) {
            binding.tasklist = container
            binding.mainListButton.setOnClickListener { listener.onClick(container) }
            binding.mainListButton.setOnLongClickListener { listener.onLongClick(container) }
        }
    }
}

interface OnContainerClickListener {
    fun onClick(c: TaskContainer)
    fun onLongClick(c: TaskContainer): Boolean
}