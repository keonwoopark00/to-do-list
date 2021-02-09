package com.edgar.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.edgar.todolist.data.model.MTask

// TaskDao.kt

@Dao
interface TaskDao {
    @Query("SELECT * FROM tb_task WHERE list_id = :id")
    fun selectTasksByListId(id: Long): LiveData<List<MTask>>

    @Query("SELECT is_checked FROM tb_task WHERE id = :id")
    fun selectIsChecked(id: Long): LiveData<Boolean>

    @Query("UPDATE tb_task SET is_checked = :value WHERE list_id = :id")
    suspend fun updateIsChecked(value: Boolean, id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: MTask)

    @Delete
    suspend fun deleteTask(task: MTask)
}