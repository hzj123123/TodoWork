package com.example.todo_work.data

import androidx.room.TypeConverter
import com.example.todo_work.data.models.Priority

/**
 *@ClassName Converter
 *@Author hzj
 *@Description
 *@Date 2023/8/26 18:07
 */
class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority) : String{
        return priority.name
    }
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}