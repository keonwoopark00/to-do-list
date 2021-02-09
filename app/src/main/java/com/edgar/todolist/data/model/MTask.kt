package com.edgar.todolist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.parcelize.Parcelize

/**
 * MTask.kt
 * Data class that models each task of a to-do-list
 */

@Parcelize
@Entity(
    tableName = "tb_task",
    foreignKeys = [ForeignKey(
        entity = TaskContainer::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("list_id")
    )]
)
data class MTask(
    @ColumnInfo(name = "task")
    var task: String,

    @ColumnInfo(name = "list_id")
    var listId: Long,

    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false
) : BaseEntity(), Parcelable
