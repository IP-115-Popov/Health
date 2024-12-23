package ru.sergey.domain.UseCase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class DeleteTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int)
    {
        tasksRepository.deleteTask(id)
    }
}