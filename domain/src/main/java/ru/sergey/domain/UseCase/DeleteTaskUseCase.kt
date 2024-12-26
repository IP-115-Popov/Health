package ru.sergey.domain.UseCase

import ru.sergey.domain.repository.TasksRepository

class DeleteTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int)
    {
        tasksRepository.deleteTask(id)
    }
}