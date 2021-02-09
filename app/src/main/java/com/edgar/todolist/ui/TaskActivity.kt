package com.edgar.todolist.ui

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.todolist.R
import com.edgar.todolist.adapter.OnTaskClickListener
import com.edgar.todolist.adapter.TaskAdapter
import com.edgar.todolist.data.model.MTask
import com.edgar.todolist.data.model.TaskContainer
import com.edgar.todolist.databinding.ActivityTaskBinding
import com.edgar.todolist.util.makeAlert
import com.edgar.todolist.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class TaskActivity : AppCompatActivity(), OnTaskClickListener {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: TaskViewModel
    private val tAdapter: TaskAdapter by lazy { TaskAdapter(this) }
    private val container: TaskContainer by lazy { intent.getParcelableExtra("container")!! }
    private val id: Long by lazy { intent.getLongExtra("id", -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = TaskViewModel.Companion.Factory(application)
        viewModel = ViewModelProvider(viewModelStore, factory).get(TaskViewModel::class.java)


        // title fragment setup
        val titleFragment = TitleFragment()
        supportFragmentManager.beginTransaction().replace(R.id.title_fragment, titleFragment)
            .commit()
        val b1 = Bundle()
        b1.putString("title", container.title)
        titleFragment.arguments = b1

        binding.detailAddButton.setOnClickListener {
            viewModel.addTaskBtnClicked(this@TaskActivity, supportFragmentManager, id)
        }

        binding.detailRv.apply {
            adapter = tAdapter
            layoutManager = LinearLayoutManager(this@TaskActivity)
            setHasFixedSize(true)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorMessage.observe(this) {
            it?.let {
                Snackbar.make(binding.taskRoot, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarDisplayed()
            }
        }

        viewModel.spinner.observe(this) {
            it.let {
                binding.spinner.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewModel.getTasks(id).observe(this) {
            tAdapter.setTasks(it)
        }
    }

    override fun onCheckClick(isChecked: Boolean, tv: TextView, id: Long?) {
        viewModel.updateIsChecked(isChecked, id!!)
        if (isChecked) {
            tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tv.paintFlags = 0
        }
    }

    override fun onLongClick(t: MTask): Boolean {
        makeAlert(
            this@TaskActivity,
            getString(R.string.delete_task_dialog_title),
            getString(R.string.delete_task_dialog_message)
        ) { _, _ -> viewModel.deleteTask(t) }
        return false
    }

}