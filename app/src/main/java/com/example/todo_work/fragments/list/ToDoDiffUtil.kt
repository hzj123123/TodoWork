package com.example.todo_work.fragments.list

import androidx.recyclerview.widget.DiffUtil
import com.example.todo_work.data.models.ToDoData

/**
 *@ClassName ToDoDiffUtil
 *@Author hzj
 *@Description
 *@Date 2023/8/31 10:53
 */
class ToDoDiffUtil : DiffUtil.ItemCallback<ToDoData>() {


    override fun areItemsTheSame(oldItem: ToDoData, newItem: ToDoData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ToDoData, newItem: ToDoData): Boolean {
        return oldItem == newItem
    }
}