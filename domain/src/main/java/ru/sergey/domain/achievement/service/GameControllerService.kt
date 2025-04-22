package ru.sergey.domain.achievement.service

import kotlinx.coroutines.flow.first
import ru.sergey.domain.gamecontroller.GameControllerRepository
import ru.sergey.domain.task.models.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class GameControllerService(
    private val gameControllerRepository: GameControllerRepository,
) {
    suspend fun onUpdateTask(oldTask: Task, newTask: Task) {
        val gameController = gameControllerRepository.gameControllerFlow.first()


        val pointsDifference = newTask.points - oldTask.points
        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

        // Получаем разницу в днях между текущей датой и timeEnd
        val timeDiffMillis = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(currentDate).time -
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(gameController.timeEnd).time

        val daysDifference = TimeUnit.DAYS.convert(timeDiffMillis, TimeUnit.MILLISECONDS)

        var streakDays = gameController.streakDays
        var pointsOnTime = gameController.pointsOnTime
        var timeStart = gameController.timeStart

        if (daysDifference <= 1) {
            pointsOnTime += pointsDifference
            // Пересчитываем streakDays на основе разницы между текущей датой и timeStart
            val streakStartDiffMillis = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(currentDate).time -
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(timeStart).time
            streakDays = TimeUnit.DAYS.convert(streakStartDiffMillis, TimeUnit.MILLISECONDS).toInt() + 1 // +1, потому что считаем текущий день
        } else {
            streakDays = 1 // Сбрасываем streakDays, если задача выполнена не вовремя (больше 1 дня прошло)
            pointsOnTime = pointsDifference // Начинаем новый streak с deltaPoints
            timeStart = currentDate // Начинаем новый период отсчета
        }

        val updatedGameController = gameController.copy(
            streakDays = streakDays,
            totalPoints = gameController.totalPoints + pointsDifference,
            pointsOnTime = pointsOnTime,
            timeStart = timeStart,
            timeEnd = currentDate,
        )

        gameControllerRepository.saveGameController(updatedGameController)
    }
}