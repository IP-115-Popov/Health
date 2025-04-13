package ru.sergey.domain.task.usecase

import ru.sergey.domain.profile.repository.TasksRepository

class DeleteTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int) {
        tasksRepository.deleteTask(id)
    }
}