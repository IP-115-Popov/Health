package ru.sergey.health.feature.task.viewmodel

import ru.sergey.domain.task.models.Task

data class TasksUIState(
    val tasks: List<Task> = emptyList()
)