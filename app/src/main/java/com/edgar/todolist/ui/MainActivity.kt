package com.edgar.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.todolist.R
import com.edgar.todolist.adapter.ContainerAdapter
import com.edgar.todolist.adapter.OnContainerClickListener
import com.edgar.todolist.data.model.TaskContainer
import com.edgar.todolist.databinding.ActivityMainBinding
import com.edgar.todolist.util.makeAlert
import com.edgar.todolist.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnContainerClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private val cAdapter: ContainerAdapter by lazy { ContainerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // initiate ViewModel
        val factory = TaskViewModel.Companion.Factory(application)
        viewModel = ViewModelProvider(viewModelStore, factory).get(TaskViewModel::class.java)

        // recycler view
        binding.mainRv.apply {
            this.adapter = cAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        // observe ViewModel LiveData
        observeViewModel()

        binding.mainAddBtn.setOnClickListener {
            viewModel.addContainerBtnClicked(
                this@MainActivity,
                supportFragmentManager
            )
        }
    }

    private fun observeViewModel() {
        viewModel.errorMessage.observe(this) {
            it?.let {
                Snackbar.make(binding.mainRoot, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarDisplayed()
            }
        }

        viewModel.spinner.observe(this) {
            it.let {
                binding.spinner.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewModel.getAllContainers().observe(this) {
            cAdapter.setContainers(it)
        }
    }

    override fun onClick(c: TaskContainer) {
        val i = Intent(this@MainActivity, TaskActivity::class.java)
        i.putExtra("container", c)
        i.putExtra("id", c.id)
        startActivity(i)
    }

    override fun onLongClick(c: TaskContainer): Boolean {
        makeAlert(
            this@MainActivity,
            getString(R.string.delete_container_dialog_title),
            getString(R.string.delete_container_dialog_message)
        ) {_, _ -> viewModel.deleteContainer(c)}
        return false
    }
}