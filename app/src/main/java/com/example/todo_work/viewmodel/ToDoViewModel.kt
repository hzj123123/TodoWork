package com.example.todo_work.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_work.data.ToDoDataBase
import com.example.todo_work.data.models.ToDoData
import com.example.todo_work.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *@ClassName ToDoViewModel
 *@Author hzj
 *@Description
 *@Date 2023/8/26 18:15
 */
class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val toDoDao = ToDoDataBase.getDataBase(application).todoDAO()

    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val getAllData: LiveData<MutableList<ToDoData>> = repository.getAllData

    val sortByLowData :LiveData<MutableList<ToDoData>> = repository.sortByLowData

    val sortByHighData :LiveData<MutableList<ToDoData>> = repository.sortByHighData

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }
    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }
    fun deleteItem(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(toDoData)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
    fun searchData(string: String) : LiveData<MutableList<ToDoData>>{
            return repository.searchData(string)
    }
}