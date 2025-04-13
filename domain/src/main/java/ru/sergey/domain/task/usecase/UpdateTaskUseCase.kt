package ru.sergey.domain.task.usecase

import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Task

class UpdateTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(task: Task) {
        tasksRepository.updateTask(task)

        SavePointUseCase(tasksRepository).execute(
            taskId = task.id,
            taskPoint = task.points
        )
    }
}