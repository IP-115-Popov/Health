package ru.sergey.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.sergey.data.storage.DataTaskDomainTaskConverter
import ru.sergey.data.storage.TaskDao
import ru.sergey.data.storage.TaskRoomDatabase
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class TaskRepositoryImp(context: Context) : TasksRepository {

    private val database: TaskRoomDatabase = TaskRoomDatabase.buildDatabase(context, DATABASE_NAME)

    private val taskDao: TaskDao = database.TaskDao()

    val dataTaskDomainTaskConverter = DataTaskDomainTaskConverter()

    override suspend fun uploadTask(task: Task) {
        taskDao.insertTask(dataTaskDomainTaskConverter.DomainTaskToDataTask(task))
    }

    override suspend fun downloadTasks(): Flow<List<Task>> {
        val res = taskDao.getTasks().map {
            it.map { task -> dataTaskDomainTaskConverter.DataTaskToDomainTask(task) }
        }
        return res
    }

    override suspend fun downloadTask(id: Int): Task? {
        val res = taskDao.getTaskById(id)?.let {dataTaskDomainTaskConverter.DataTaskToDomainTask(it)}
        return res
    }

    override suspend fun deleteTask(id: Int) {
        taskDao.deleteById(id)
    }

    override suspend fun updateTaskPoints(task: Task) {
        taskDao.updateTask(dataTaskDomainTaskConverter.DomainTaskToDataTask(task))
    }

    companion object {
        private const val DATABASE_NAME = "task_database_1.db"
    }
}