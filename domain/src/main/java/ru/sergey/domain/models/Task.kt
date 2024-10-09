package ru.sergey.domain.models

data class Task(
    val id : Int,
    val title : String,
    val description: String = "",
    val points : Int = 0,
    val targetPoints : Int = 100
)