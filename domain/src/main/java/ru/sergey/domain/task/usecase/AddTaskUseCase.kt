package ru.sergey.domain.task.usecase

import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Task

class AddTaskUseCase(
    private val tasksRepository: TasksRepository,
    ) {
    suspend fun execute(task: Task) {
        val taskId = tasksRepository.saveTask(task)
        SavePointUseCase(
            tasksRepository = tasksRepository,
        ).execute(
            taskId = taskId,
            taskPoint = task.points
        )
    }
}