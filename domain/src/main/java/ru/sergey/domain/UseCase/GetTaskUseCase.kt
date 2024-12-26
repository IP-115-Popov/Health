package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class GetTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int): Task? {
        return tasksRepository.downloadTask(id)
    }
}