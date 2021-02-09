package com.edgar.todolist.data

import android.content.Context
import androidx.room.*
import com.edgar.todolist.data.dao.TaskContainerDao
import com.edgar.todolist.data.dao.TaskDao
import com.edgar.todolist.data.model.MTask
import com.edgar.todolist.data.model.TaskContainer

/**
 * AppDatabase.kt
 * Contains DB information
 */

@Database(
    version = 3, exportSchema = false,
    entities = [MTask::class, TaskContainer::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskContainerDao(): TaskContainerDao

    companion object {
        private const val DATABASE_NAME = "to-do-list.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}