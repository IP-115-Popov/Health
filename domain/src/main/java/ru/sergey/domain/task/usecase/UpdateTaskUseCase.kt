package ru.sergey.domain.task.usecase

import ru.sergey.domain.achievement.service.GameControllerService
import ru.sergey.domain.gamecontroller.GameControllerRepository
import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Task

class UpdateTaskUseCase(
    private val tasksRepository: TasksRepository,
    private val gameControllerRepository: GameControllerRepository,
) {
    private val gameControllerService = GameControllerService(gameControllerRepository = gameControllerRepository)

    suspend fun execute(task: Task) {
        val oldTask = tasksRepository.downloadTask(task.id) ?: task
        tasksRepository.updateTask(task)

        gameControllerService.onUpdateTask(oldTask = oldTask, newTask = task)

        SavePointUseCase(tasksRepository).execute(
            taskId = task.id,
            taskPoint = task.points
        )
    }
}