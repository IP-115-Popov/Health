package ru.sergey.domain.UseCase

import kotlinx.coroutines.flow.first
import ru.sergey.domain.models.Points
import ru.sergey.domain.repository.TasksRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavePointUseCase (private val tasksRepository: TasksRepository) {
    suspend fun execute(taskId: Int, taskPoint: Int)
    {
        val pointsList = tasksRepository.getPointByTaskId(taskId)

        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

        val points = pointsList.first()

        val existingPoint = points.find { it.date == currentDate }

        // Если запись уже существует, обновляем её
        if (existingPoint != null) {
            tasksRepository.savePoint(
                Points(
                    pointId = existingPoint.pointId,
                    taskId = taskId,
                    date = currentDate,
                    points = taskPoint
                )
            )
        } else {
            // Если записи нет, создаем новую запись для текущего дня
            tasksRepository.savePoint(
                Points(
                    pointId = 0,
                    taskId = taskId,
                    date = currentDate,
                    points = taskPoint
                )
            )
        }
    }
}