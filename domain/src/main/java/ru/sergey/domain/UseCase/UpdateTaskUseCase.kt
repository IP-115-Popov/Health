package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class UpdateTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun exectute(task: Task)
    {
        tasksRepository.updateTaskPoints(task)
    }
}