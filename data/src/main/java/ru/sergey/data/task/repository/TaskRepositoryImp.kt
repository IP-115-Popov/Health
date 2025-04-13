package ru.sergey.data.task.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.sergey.data.task.db.DataTaskDomainTaskConverter
import ru.sergey.data.task.db.TaskDao
import ru.sergey.data.task.db.TaskPointsEntity
import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.models.Points
import ru.sergey.domain.task.models.Task

class TaskRepositoryImp(val taskDao: TaskDao) : TasksRepository {

    val dataTaskDomainTaskConverter = DataTaskDomainTaskConverter()


    override suspend fun saveTask(task: Task): Int {
        return taskDao.insertTask(dataTaskDomainTaskConverter.domainTaskToDataTask(task)).toInt()
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(dataTaskDomainTaskConverter.domainTaskToDataTask(task))
    }

    override suspend fun downloadTasks(): Flow<List<Task>> {
        val res = taskDao.getTasks().map {
            it.map { task -> dataTaskDomainTaskConverter.dataTaskToDomainTask(task) }
        }
        return res
    }

    override suspend fun downloadTask(id: Int): Task? {
        val res =
            taskDao.getTaskById(id)?.let { dataTaskDomainTaskConverter.dataTaskToDomainTask(it) }
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