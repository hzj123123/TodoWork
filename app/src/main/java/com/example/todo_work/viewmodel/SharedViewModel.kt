package com.example.todo_work.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_work.R
import com.example.todo_work.data.models.Priority
import com.example.todo_work.data.models.ToDoData

/**
 *@ClassName SharedViewModel
 *@Author hzj
 *@Description
 *@Date 2023/8/29 9:27
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val emptyDatabase:MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>){
        emptyDatabase.value = toDoData.isEmpty()
    }

    val listener:AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0->{((parent?.getChildAt(0)) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1->{((parent?.getChildAt(0)) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2->{((parent?.getChildAt(0)) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

    fun parsePriority(mPriority: String): Priority {
        return when (mPriority) {
            "高优先级" -> {
                Priority.HIGH
            }
            "中等优先级" -> {
                Priority.MEDIUM
            }
            "低优先级" -> {
                Priority.LOW
            }
            else -> {
                Priority.LOW
            }
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

}