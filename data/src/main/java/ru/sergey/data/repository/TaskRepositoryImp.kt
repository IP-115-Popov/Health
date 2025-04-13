package ru.sergey.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.sergey.data.storage.DataTaskDomainTaskConverter
import ru.sergey.data.storage.TaskDao
import ru.sergey.data.storage.TaskPointsEntity
import ru.sergey.domain.models.Points
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class TaskRepositoryImp(val taskDao: TaskDao) : TasksRepository {

    val dataTaskDomainTaskConverter = DataTaskDomainTaskConverter()


    override suspend fun saveTask(task: Task): Int {
        return taskDao.insertTask(dataTaskDomainTaskConverter.DomainTaskToDataTask(task)).toInt()
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(dataTaskDomainTaskConverter.DomainTaskToDataTask(task))
    }

    override suspend fun downloadTasks(): Flow<List<Task>> {
        val res = taskDao.getTasks().map {
            it.map { task -> dataTaskDomainTaskConverter.DataTaskToDomainTask(task) }
        }
        return res
    }

    override suspend fun downloadTask(id: Int): Task? {
        val res =
            taskDao.getTaskById(id)?.let { dataTaskDomainTaskConverter.DataTaskToDomainTask(it) }
        return res
    }

    override suspend fun deleteTask(id: Int) {
        taskDao.deleteById(id)
    }

    //Points
    override suspend fun savePoint(points: Points) {
        taskDao.insertTaskPoints(TaskPointsEntity.toEntity(points))
    }

    override suspend fun getPointByTaskId(idTask: Int): Flow<List<Points>> {
        return taskDao.getTaskPoints(idTask).map { it ->
            it.map { points -> TaskPointsEntity.fromEntity(points) }
        }
    }
}