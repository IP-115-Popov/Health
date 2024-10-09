package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class AddTaskUseCase(private val tasksRepository: TasksRepository) {
    fun exectute(task: Task)
    {
        tasksRepository.uploadTask(task)
    }
}