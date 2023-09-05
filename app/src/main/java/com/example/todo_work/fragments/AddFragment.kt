package com.example.todo_work.fragments

import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo_work.R
import com.example.todo_work.viewmodel.ToDoViewModel
import com.example.todo_work.data.models.Priority
import com.example.todo_work.data.models.ToDoData
import com.example.todo_work.databinding.FragmentAddBinding
import com.example.todo_work.viewmodel.SharedViewModel


class AddFragment : BaseFragment<FragmentAddBinding>() {
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun initViews() {
        setHasOptionsMenu(true)
    }

    override fun initListener() {
        binding.prioritiesSpinner.onItemSelectedListener = sharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDo() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        var validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            val data = ToDoData(id = 0, title = mTitle, description = mDescription, priority = sharedViewModel.parsePriority(mPriority))
            mToDoViewModel.insertData(data)
            Toast.makeText(requireContext(), "添加数据成功", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "添加数据失败", Toast.LENGTH_SHORT).show()
        }

    }


}