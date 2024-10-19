package ru.sergey.domain.models

data class Player(
    val id : Long,
    val name : String,
    val avatar : String,
    val level : Int,
    val closeTasksId : List<Int>,
    val openTasksId: List<Int>
)