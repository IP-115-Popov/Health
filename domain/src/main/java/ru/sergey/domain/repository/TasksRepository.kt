package ru.sergey.domain.repository

import ru.sergey.domain.models.Task

interface TasksRepository {
    fun uploadTask(task: Task)
    fun downloadTasks(): List<Task>
}