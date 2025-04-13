package ru.sergey.domain.task.usecase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Task

class DownloadTasksUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(): Flow<List<Task>> {
        return tasksRepository.downloadTasks()
    }
}