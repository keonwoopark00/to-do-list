package com.edgar.todolist.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.Date

/**
 * BaseEntity.kr
 * provides two auto-generated columns: id and created_date
 */

open class BaseEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "created_date")
    var createdDate: String = Date().toString()
)