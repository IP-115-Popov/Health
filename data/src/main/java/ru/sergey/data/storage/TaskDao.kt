package ru.sergey.data.storage

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM ${TaskStorage.TABLE_NAME}")
    fun getTasks(): Flow<List<TaskStorage>>

    @Upsert
    fun insertTask(taskStorage : TaskStorage)

    @Update
    suspend fun updateTask(task: TaskStorage)

    @Query("SELECT * FROM ${TaskStorage.TABLE_NAME} WHERE taskId = :taskId LIMIT 1")
    fun getTaskById(taskId: Int): TaskStorage?

    @Query("DELETE FROM ${TaskStorage.TABLE_NAME} WHERE taskId = :id")
    fun deleteById(id: Int)
}