package com.edgar.todolist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

// TaskContainer.kt

@Parcelize
@Entity(tableName = "tb_task_container")
data class TaskContainer(
    @ColumnInfo(name = "title")
    var title: String,
) : BaseEntity(), Parcelable


/**
 * TasksTypeConverter
 * converter class to store List<MTask> as a column of Entity TaskList
 */
//class TasksTypeConverter {
//    @TypeConverter
//    fun fromListToJson(value: List<MTask>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun fromJsonToList(jsonString: String): List<MTask> {
////        val listType = object : TypeToken<ArrayList<MTask>>(){}.type
////        return Gson().fromJson(jsonString, listType)
//        return Gson().fromJson(jsonString, Array<MTask>::class.java).toList()
//    }
//}
