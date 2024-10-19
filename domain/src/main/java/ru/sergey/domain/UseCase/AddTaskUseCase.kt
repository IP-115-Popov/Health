package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository
import kotlin.coroutines.suspendCoroutine

class AddTaskUseCase(private val tasksRepository: TasksRepository) {
    suspend fun exectute(task: Task)
    {
        tasksRepository.uploadTask(task)
    }
}