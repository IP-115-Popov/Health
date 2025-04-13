package ru.sergey.data.task.db

import ru.sergey.domain.task.models.Task

class DataTaskDomainTaskConverter {
    fun dataTaskToDomainTask(task : TaskStorage) : Task {
        return with(task) {
            Task(
                id = id,
                title = title,
                description = description,
                points = points,
                targetPoints = targetPoints,
                measureUnit = measureUnit
            )
        }
    }
    fun domainTaskToDataTask(task : Task) : TaskStorage {
        return with(task) {
            TaskStorage(
                id = id,
                title = title,
                description = description,
                points = points,
                targetPoints = targetPoints,
                measureUnit = measureUnit
            )
        }
    }
}