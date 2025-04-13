package ru.sergey.domain.task.usecase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Points

class GetPointsUseCase(private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int): Flow<List<Points>> {
        return tasksRepository.getPointByTaskId(id)
    }
}