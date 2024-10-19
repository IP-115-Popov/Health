package ru.sergey.domain.repository

import ru.sergey.domain.models.Task

interface TasksRepository {
    suspend fun uploadTask(task: Task)
    suspend fun  downloadTasks(): List<Task>
    suspend fun updateTaskPoints(task: Task)
}