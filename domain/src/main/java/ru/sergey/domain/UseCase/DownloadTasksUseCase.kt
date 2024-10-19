package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class DownloadTasksUseCase(private val tasksRepository: TasksRepository) {
    suspend fun exectute() : List<Task>
    {
        return tasksRepository.downloadTasks()
    }
}