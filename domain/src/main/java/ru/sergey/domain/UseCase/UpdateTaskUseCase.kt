package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class UpdateTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(task: Task)
    {
        tasksRepository.updateTask(task)

        SavePointUseCase(tasksRepository).execute(
            taskId = task.id,
            taskPoint = task.points
        )
    }
}