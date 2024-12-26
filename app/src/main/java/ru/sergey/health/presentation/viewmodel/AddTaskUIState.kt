package ru.sergey.health.presentation.viewmodel

data class AddTaskUIState(
    val id: Int = 0,
    val titleText: String = "",
    val descriptionText: String = "",
    val points: Int = 0,
    val targetPointsText: Int = 0,
    val measureUnitText: String = "",
)