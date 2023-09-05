package com.example.todo_work.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo_work.data.models.ToDoData

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<MutableList<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :s")
    fun searchData(s: String): LiveData<MutableList<ToDoData>>

    @Query(
        "SELECT * FROM todo_table ORDER BY CASE " +
                "WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END"
    )
    fun sortByHighData(): LiveData<MutableList<ToDoData>>


    @Query(
        "SELECT * FROM todo_table ORDER BY CASE " +
                "WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END"
    )
    fun sortByLowData(): LiveData<MutableList<ToDoData>>
}