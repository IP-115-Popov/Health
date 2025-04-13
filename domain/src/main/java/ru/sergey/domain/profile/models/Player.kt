package ru.sergey.domain.profile.models

data class Player(
    val name: String = "",
    val avatar: String = "",
    val level: Int = 0,
    val ex: Int = 0,
    val closeTasksId: List<Int> = emptyList(),
    val openTasksId: List<Int> = emptyList()
)