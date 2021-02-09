package com.edgar.todolist.data.repository

import androidx.lifecycle.LiveData
import com.edgar.todolist.data.dao.TaskContainerDao
import com.edgar.todolist.data.model.TaskContainer

// TaskContainerRepository.kt

class TaskContainerRepository(private val dataRepository: DataRepository) {
    companion object {
        @Volatile
        private var INSTANCE: TaskContainerRepository? = null

        // singleton with synchronized
        fun getInstance(dataRepository: DataRepository): TaskContainerRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskContainerRepository(dataRepository).also { INSTANCE = it }
            }
        }
    }

    private val taskContainerDao: TaskContainerDao
        get() = dataRepository.database.taskContainerDao()

    fun getAll(): LiveData<List<TaskContainer>> = taskContainerDao.getAll()

    suspend fun insertContainer(container: TaskContainer) {
        taskContainerDao.insertContainer(container)
    }

    suspend fun deleteContainer(container: TaskContainer) {
        taskContainerDao.deleteContainer(container)
    }
}