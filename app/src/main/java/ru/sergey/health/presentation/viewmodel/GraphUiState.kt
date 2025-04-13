package ru.sergey.health.presentation.viewmodel

import ru.sergey.domain.models.Points

data class GraphUiState(
    val pointsList: List<Points> = emptyList(),
)