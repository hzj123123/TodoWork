package com.example.todo_work.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.todo_work.R
import com.example.todo_work.data.models.ToDoData
import com.example.todo_work.databinding.RowLayoutBinding

/**
 *@ClassName ListAdapter
 *@Author hzj
 *@Description
 *@Date 2023/8/29 10:59
 */
class ListAdapter : BaseQuickAdapter<ToDoData, ListAdapter.MyViewHolder>(R.layout.row_layout) {

    override fun convert(holder: MyViewHolder, item: ToDoData) {
        holder.bind(item)
    }

    class MyViewHolder(private val binding: RowLayoutBinding) : BaseViewHolder(binding.root) {
        fun bind(item: ToDoData) {
            binding.toData = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyViewHolder(binding)
            }

        }
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // 创建绑定对象
        return MyViewHolder.from(parent)
    }


}