package com.edgar.todolist.data.repository

import android.app.Application
import com.edgar.todolist.data.AppDatabase

// DataRepository.kt

class DataRepository(application: Application) {
    val database: AppDatabase = AppDatabase.getDatabase(application)

    fun getTaskContainerRepo() = TaskContainerRepository.getInstance(this)
    fun getTaskRepo() = TaskRepository.getInstance(this)
}

class DataHandleError(message: String, cause: Throwable) : Throwable(message, cause)