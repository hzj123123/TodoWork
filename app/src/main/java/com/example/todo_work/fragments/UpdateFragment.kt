package com.example.todo_work.fragments

import android.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo_work.R
import com.example.todo_work.data.models.ToDoData
import com.example.todo_work.databinding.FragmentUpdateBinding
import com.example.todo_work.viewmodel.SharedViewModel
import com.example.todo_work.viewmodel.ToDoViewModel


class UpdateFragment : BaseFragment<FragmentUpdateBinding>() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    override fun initViews() {
        setHasOptionsMenu(true)
        binding.args = args
//        binding.curTitleEt.setText(args.currentItem.title)
//        binding.curDescriptionEt.setText(args.currentItem.description)
//        binding.curPrioritiesSpinner.setSelection(sharedViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.curPrioritiesSpinner.onItemSelectedListener = sharedViewModel.listener
    }

    override fun initListener() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("确定") { _, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "删除成功", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("取消") { _, _ -> }
        builder.setTitle("删除 ${args.currentItem.title}?")
        builder.setMessage("确定要删除吗？")
        builder.create().show()

    }


    private fun updateItem() {
        val mTitle = binding.curTitleEt.text.toString()
        val mPriority = binding.curPrioritiesSpinner.selectedItem.toString()
        val mDescription = binding.curDescriptionEt.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            val item = ToDoData(args.currentItem.id, mTitle, sharedViewModel.parsePriority(mPriority), mDescription)
            mToDoViewModel.updateData(item)
            Toast.makeText(requireContext(), "更新数据成功", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "更新数据失败", Toast.LENGTH_SHORT).show()
        }
    }
}