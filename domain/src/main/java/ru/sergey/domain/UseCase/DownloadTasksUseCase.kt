package ru.sergey.domain.UseCase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class DownloadTasksUseCase(private val tasksRepository: TasksRepository) {
    suspend fun exectute() : Flow<List<Task>>
    {
        return tasksRepository.downloadTasks()
    }
}