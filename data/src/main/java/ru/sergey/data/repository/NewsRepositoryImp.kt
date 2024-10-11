package ru.sergey.data.repository

import ru.sergey.domain.models.Task
import ru.sergey.domain.repository.TasksRepository

class NewsRepositoryImp : TasksRepository {
    val storage = mutableListOf(
        Task(0, "task0", "abobaababababababababb", 50,100),
        Task(1, "task1", "abobaababababababababb", 50,100),
        Task(2, "task2", "abobaababababababababb", 50,100),
        Task(3, "task3", "abobaababababababababb", 50,100),
        Task(4, "task4", "abobaababababababababb", 50,100),
        Task(5, "task5", "abobaababababababababb", 50,100),
        Task(6, "task6", "abobaababababababababb", 50,100),
        Task(7, "task7", "abobaababababababababb", 50,100),
    )
    override fun uploadTask(task: Task) {
        storage.add(task)
    }

    override fun downloadTasks(): List<Task> {
        return storage
    }
}