package ru.sergey.data.repository

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.sergey.data.storage.DataTaskDomainTaskConverter
import ru.sergey.data.storage.TaskDao
import ru.sergey.data.storage.TaskRoomDatabase
import ru.sergey.data.storage.TaskStorage
import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class TaskRepositoryImp(context: Context) : TasksRepository {

    private val database: TaskRoomDatabase = TaskRoomDatabase.buildDatabase(context, DATABASE_NAME)

    private val taskDao : TaskDao = database.TaskDao()

    val dataTaskDomainTaskConverter = DataTaskDomainTaskConverter()

    override suspend fun uploadTask(task: Task) {
        taskDao.insertTask(dataTaskDomainTaskConverter.DomainTaskToDataTask(task))
    }

    override suspend fun downloadTasks(): List<Task> {
        val rez = taskDao.getTasks().map { it -> dataTaskDomainTaskConverter.DataTaskToDomainTask(it)}
        return rez
    }

    override suspend fun updateTaskPoints(task: Task) {
        taskDao.updateTask(dataTaskDomainTaskConverter.DomainTaskToDataTask(task))
    }

    companion object {
        private const val DATABASE_NAME = "task_database_1.db"
    }
}