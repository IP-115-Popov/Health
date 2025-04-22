package ru.sergey.domain.gamecontroller

data class GameController(
    val streakDays: Int = 0,
    val totalPoints: Int = 0,
    val pointsOnTime: Int = 0,
    val timeStart: String = "00.00.0000",
    val timeEnd: String = "00.00.0000",
)