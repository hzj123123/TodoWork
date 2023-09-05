package com.example.todo_work.data.repository

import androidx.lifecycle.LiveData
import com.example.todo_work.data.ToDoDao
import com.example.todo_work.data.models.ToDoData

/**
 *@ClassName ToDoRepository
 *@Author hzj
 *@Description
 *@Date 2023/8/26 18:10
 */
class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData = toDoDao.getAllData()
    val sortByHighData = toDoDao.sortByHighData()
    val sortByLowData = toDoDao.sortByLowData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData) {
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun searchData(string: String): LiveData<MutableList<ToDoData>> {
        return toDoDao.searchData(string)
    }
}