package ru.sergey.health.presentation.viewmodel

import ru.sergey.domain.models.Points
import ru.sergey.domain.models.Task

data class GraphUiState(
    val pointsList: List<Points> = emptyList(),
)