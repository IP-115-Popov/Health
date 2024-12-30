package ru.sergey.health.model

import android.net.Uri
import ru.sergey.domain.models.Player

data class PlayerUIModel(
    val name : String = "",
    val avatar : Uri? = null,
    val level : Int = 0,
    val ex: Int = 0,
    val closeTasksId : List<Int> = emptyList(),
    val openTasksId: List<Int> = emptyList()
) {
    companion object {
        fun fromPlayer(player: Player) = with(player) {
            val avatar1 = Uri.parse(avatar)
            PlayerUIModel(
                name = name,
                avatar = avatar1,
                level = level,
                ex = ex,
                closeTasksId = closeTasksId,
                openTasksId = openTasksId,
            )
        }
    }

    fun toPlayer(): Player {
        val avatar1 = avatar.toString()
        return Player(
        name = name,
        avatar = avatar1,
        level = level,
        ex = ex,
        closeTasksId = closeTasksId,
        openTasksId = openTasksId,
    )
    }
}
