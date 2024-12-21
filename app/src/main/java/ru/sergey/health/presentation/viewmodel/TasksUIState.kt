package ru.sergey.health.presentation.viewmodel

import ru.sergey.domain.models.Task

data class TasksUIState (
    val tasks: List<Task> = emptyList()
)