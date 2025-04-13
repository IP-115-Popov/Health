package ru.sergey.domain.task.models

data class Points(
    val pointId: Int,
    val taskId: Int,
    val date: String,
    val points: Int
)