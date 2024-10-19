package ru.sergey.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.sergey.domain.models.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM ${TaskStorage.TABLE_NAME}")
    fun getTasks(): List<TaskStorage>

    @Insert
    fun insertTask(taskStorage : TaskStorage)

    @Update
    suspend fun updateTask(task: TaskStorage)

}