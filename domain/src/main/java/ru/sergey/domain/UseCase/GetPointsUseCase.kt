package ru.sergey.domain.UseCase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Points
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class GetPointsUseCase (private val tasksRepository: TasksRepository) {
    suspend fun execute(id: Int): Flow<List<Points>>
    {
        return tasksRepository.getPointByTaskId(id)
    }
}