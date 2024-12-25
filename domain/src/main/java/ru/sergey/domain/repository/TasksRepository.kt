package ru.sergey.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Points
import ru.sergey.domain.models.Task

interface TasksRepository {
    suspend fun updateTask(task: Task)
    suspend fun saveTask(task: Task) : Int

    suspend fun downloadTasks(): Flow<List<Task>>
    suspend fun downloadTask(id: Int): Task?
    suspend fun deleteTask(id: Int)
    suspend fun savePoint(points: Points)
    suspend fun getPointByTaskId(idTask: Int): Flow<List<Points>>
}