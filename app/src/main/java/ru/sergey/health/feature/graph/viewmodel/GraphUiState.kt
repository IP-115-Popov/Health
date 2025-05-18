package ru.sergey.health.feature.graph.viewmodel

import ru.sergey.domain.task.models.Points

data class GraphUiState(
    val pointsList: List<Points> = emptyList(),
    val graphMode: GraphMode = GraphMode.TotalNumberOfPoints
)

enum class GraphMode {
    DailyPointsGrowth,
    GrowthRate,
    TotalNumberOfPoints,
    TotalNumberOfPointsWithMissedDays,
}