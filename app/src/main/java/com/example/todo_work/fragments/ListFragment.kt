package com.example.todo_work.fragments

import android.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.todo_work.R
import com.example.todo_work.adapter.ListAdapter
import com.example.todo_work.adapter.SwipeToDelete
import com.example.todo_work.data.models.ToDoData
import com.example.todo_work.databinding.FragmentListBinding
import com.example.todo_work.viewmodel.SharedViewModel
import com.example.todo_work.viewmodel.ToDoViewModel
import com.google.android.material.snackbar.Snackbar


class ListFragment : BaseFragment<FragmentListBinding>(), OnQueryTextListener {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun initViews() {
        setHasOptionsMenu(true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        swipeToDelete(binding.recyclerView)
        //默认的动画  也可以自定义动画
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
        toDoViewModel.getAllData.observe(viewLifecycleOwner) {
            sharedViewModel.checkIfDatabaseEmpty(it)
            adapter.setList(it)
        }

//        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
//            showEmptyDatabaseViews(it)
//        })

        binding.lifecycleOwner = this
        binding.mSharedViewModel = sharedViewModel
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.getItem(viewHolder.adapterPosition)
                toDoViewModel.deleteItem(item)
//                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(requireContext(), "删除成功", Toast.LENGTH_SHORT).show()
                restoreDeleteData(viewHolder.itemView, item)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteData(view: View, item: ToDoData) {
        val snackBar = Snackbar.make(view, "已删除 ${item.title}", Snackbar.LENGTH_LONG)
        snackBar.setAction("回滚") {
            toDoViewModel.insertData(item)
//            adapter.notifyItemInserted(position)
        }
        snackBar.show()
    }
//    private fun showEmptyDatabaseViews(check: Boolean) {
//        if (check){
//            binding.noDataIv.visibility = View.VISIBLE
//            binding.noDataTv.visibility = View.VISIBLE
//        }else{
//            binding.noDataIv.visibility = View.INVISIBLE
//            binding.noDataTv.visibility = View.INVISIBLE
//        }
//    }

    override fun initListener() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> confirmRemoval()
            R.id.menu_priorities_high-> toDoViewModel.sortByHighData.observe(this){ adapter.setList(it)}
            R.id.menu_priorities_low-> toDoViewModel.sortByLowData.observe(this){ adapter.setList(it)}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("确定") { _, _ ->
            toDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "删除全部成功", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("取消") { _, _ -> }
        builder.setTitle("删除全部？")
        builder.setMessage("确定要删除全部记录？")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(string: String) {
        val s = "%${string}%"
        toDoViewModel.searchData(s).observe(this) {
            it.let {
                adapter.setList(it)
            }
        }

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchThroughDatabase(newText)
        }
        return false
    }

}