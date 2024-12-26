package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class AddTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun exectute(task: Task)
    {
       val taskId = tasksRepository.saveTask(task)
        SavePointUseCase(tasksRepository).execute(
            taskId = taskId,
            taskPoint = task.points
        )
    }
}