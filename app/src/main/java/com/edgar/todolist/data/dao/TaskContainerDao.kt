package com.edgar.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.edgar.todolist.data.model.TaskContainer

// TaskContainerDao.kt

@Dao
interface TaskContainerDao {
    @Query("SELECT * FROM tb_task_container")
    fun getAll(): LiveData<List<TaskContainer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContainer(container: TaskContainer)

    @Delete
    suspend fun deleteContainer(container: TaskContainer)
}