package ru.sergey.data.storage

import ru.sergey.domain.models.Task

class DataTaskDomainTaskConverter {
    fun DataTaskToDomainTask(task : TaskStorage) : Task {
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
    fun DomainTaskToDataTask(task : Task) : TaskStorage {
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