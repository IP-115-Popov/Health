package ru.sergey.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Task

interface TasksRepository {
    suspend fun uploadTask(task: Task)
    suspend fun downloadTasks(): Flow<List<Task>>
    suspend fun downloadTask(id: Int): Task?
    suspend fun updateTaskPoints(task: Task)
    suspend fun deleteTask(id: Int)

}