package ru.sergey.health.feature.graph.viewmodel

import ru.sergey.domain.task.models.Points

data class GraphUiState(
    val pointsList: List<Points> = emptyList(),
)