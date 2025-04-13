package ru.sergey.data.task.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    //task
    @Query("SELECT * FROM ${TaskStorage.TABLE_NAME}")
    fun getTasks(): Flow<List<TaskStorage>>

    @Upsert
    fun updateTask(taskStorage : TaskStorage)

    @Insert
    fun insertTask(taskStorage : TaskStorage): Long

    @Query("SELECT * FROM ${TaskStorage.TABLE_NAME} WHERE taskId = :taskId LIMIT 1")
    fun getTaskById(taskId: Int): TaskStorage?

    @Query("DELETE FROM ${TaskStorage.TABLE_NAME} WHERE taskId = :id")
    fun deleteById(id: Int)

    //point

    // Вставить очки для задачи
    @Upsert
    fun insertTaskPoints(taskPoints: TaskPointsEntity)

    // Получить все очки по задаче
    @Query("SELECT * FROM task_points WHERE ${TaskPointsEntity.TASK_ID} = :taskId")
    fun getTaskPoints(taskId: Int): Flow<List<TaskPointsEntity>>
}