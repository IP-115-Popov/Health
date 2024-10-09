package ru.sergey.data.repository

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class NewsRepositoryImp : TasksRepository {
    override fun uploadTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun downloadTasks(): List<Task> {
        TODO("Not yet implemented")
    }
}