package com.edgar.todolist.viewmodel

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.edgar.todolist.R
import com.edgar.todolist.data.model.MTask
import com.edgar.todolist.data.model.TaskContainer
import com.edgar.todolist.data.repository.DataHandleError
import com.edgar.todolist.data.repository.DataRepository
import com.edgar.todolist.data.repository.TaskContainerRepository
import com.edgar.todolist.data.repository.TaskRepository
import com.edgar.todolist.util.BottomSheetHelper
import com.edgar.todolist.util.displayBottomSheet
import kotlinx.coroutines.launch

// TaskViewModel.kt

class TaskViewModel(application: Application) : ViewModel() {
    companion object {
        class Factory(
            private val application: Application,
        ) :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TaskViewModel(application) as T
            }
        }
    }

    // Show a loading spinner if true
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    // error message when error happens
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    // get repositories
    private val repository: DataRepository = DataRepository(application)
    private val containerRepo: TaskContainerRepository
        get() = repository.getTaskContainerRepo()
    private val taskRepo: TaskRepository
        get() = repository.getTaskRepo()

    /**
     * container related
     */
    fun getAllContainers(): LiveData<List<TaskContainer>> = containerRepo.getAll()

    private fun insertContainer(c: TaskContainer) = launchDataLoad {
        containerRepo.insertContainer(c)
    }

    fun deleteContainer(c: TaskContainer) = launchDataLoad {
        containerRepo.deleteContainer(c)
    }

    // called when add container button is clicked
    fun addContainerBtnClicked(context: Context, fm: FragmentManager) = displayBottomSheet(
        BottomSheetHelper(
            title = context.getString(R.string.add_container),
            tag = context.getString(R.string.add_container),
            manager = fm
        )
    ) {
        val c = TaskContainer(it)
        insertContainer(c)
    }

    /**
     * task related
     */
    fun getTasks(listId: Long): LiveData<List<MTask>> = taskRepo.selectTasksByListId(listId)

    fun getIsChecked(id: Long): LiveData<Boolean> = taskRepo.selectIsChecked(id)

    fun updateIsChecked(isChecked: Boolean, listId: Long) = launchDataLoad {
        taskRepo.updateIsChecked(isChecked, listId)
    }

    fun insertTask(t: MTask) = launchDataLoad {
        taskRepo.insertTask(t)
    }

    fun deleteTask(t: MTask) = launchDataLoad {
        taskRepo.deleteTask(t)
    }

    // called when add task button is clicked
    fun addTaskBtnClicked(context: Context, fm: FragmentManager, listId: Long) =
        displayBottomSheet(
            BottomSheetHelper(
                title = context.getString(R.string.add_task_cap),
                tag = context.getString(R.string.add_task_cap),
                manager = fm
            )
        ) {
            val t = MTask(it, listId)
            insertTask(t)
        }

    /**
     * helper functions
     */
    // called after the UI shows snackbar
    fun onSnackbarDisplayed() {
        _errorMessage.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: DataHandleError) {
                _errorMessage.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}