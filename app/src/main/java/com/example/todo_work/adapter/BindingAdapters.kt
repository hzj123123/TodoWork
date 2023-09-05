package com.example.todo_work.adapter

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todo_work.R
import com.example.todo_work.data.models.Priority
import com.example.todo_work.data.models.ToDoData
import com.example.todo_work.fragments.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *@ClassName BindingAdapter
 *@Author hzj
 *@Description
 *@Date 2023/8/30 14:59
 */
class BindingAdapters {
    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            if (emptyDatabase.value == true) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }
        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, mPriority: Priority) {
            when (mPriority) {
                Priority.HIGH -> {
                    view.setSelection(0)
                }
                Priority.MEDIUM -> {
                    view.setSelection(1)
                }
                Priority.LOW -> {
                    view.setSelection(2)
                }
            }
        }
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view: CardView, mPriority: Priority) {
            when(mPriority){
                Priority.HIGH->{view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.red))}
                Priority.MEDIUM->{view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.yellow))}
                Priority.LOW->{view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.green))}
            }
        }
        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, curItem: ToDoData) {
            view.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(curItem)
                view.findNavController().navigate(action)
            }
        }
    }
}