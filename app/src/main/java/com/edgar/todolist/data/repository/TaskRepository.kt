package com.edgar.todolist.data.repository

import androidx.lifecycle.LiveData
import com.edgar.todolist.data.dao.TaskDao
import com.edgar.todolist.data.model.MTask

// TaskRepository.kt

class TaskRepository(private val dataRepository: DataRepository) {
    companion object {
        @Volatile
        private var INSTANCE: TaskRepository? = null

        // singleton with synchronized
        fun getInstance(dataRepository: DataRepository): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository(dataRepository).also { INSTANCE = it }
            }
        }
    }

    private val taskDao: TaskDao
        get() = dataRepository.database.taskDao()

    fun selectTasksByListId(id: Long): LiveData<List<MTask>> = taskDao.selectTasksByListId(id)

    fun selectIsChecked(id: Long): LiveData<Boolean> = taskDao.selectIsChecked(id)

    suspend fun updateIsChecked(value: Boolean, id: Long) {
        taskDao.updateIsChecked(value, id)
    }

    suspend fun insertTask(task: MTask) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: MTask) {
        taskDao.deleteTask(task)
    }
}