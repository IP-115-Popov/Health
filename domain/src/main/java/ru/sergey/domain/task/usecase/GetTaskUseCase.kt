package ru.sergey.domain.task.usecase

import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Task

class GetTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int): Task? {
        return tasksRepository.downloadTask(id)
    }
}