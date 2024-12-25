package ru.sergey.domain.UseCase

import kotlinx.coroutines.flow.first
import ru.sergey.domain.models.Points
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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