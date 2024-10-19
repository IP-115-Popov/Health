package ru.sergey.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM ${TaskStorage.TABLE_NAME}")
    fun getTasks(): List<TaskStorage>

    @Insert
    fun insertTask(taskStorage : TaskStorage)

}