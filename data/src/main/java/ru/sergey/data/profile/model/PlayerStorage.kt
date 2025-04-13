package ru.sergey.data.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sergey.domain.profile.models.Player

@Serializable
data class PlayerStorage(
    @SerialName("name") val name: String = "",
    @SerialName("avatar") val avatar: String = "",
    @SerialName("level") val level: Int = 0,
    @SerialName("ex") val ex: Int = 0,
    @SerialName("closeTasksId") val closeTasksId: List<Int> = emptyList(),
    @SerialName("openTasksId") val openTasksId: List<Int> = emptyList()
) {
    companion object {
        fun fromPlayer(player: Player) = with(player) {
            PlayerStorage(
                name = name,
                avatar = avatar,
                level = level,
                ex = ex,
                closeTasksId = closeTasksId,
                openTasksId = openTasksId,
            )
        }
    }

    fun toPlayer() = Player(
        name = name,
        avatar = avatar,
        level = level,
        ex = ex,
        closeTasksId = closeTasksId,
        openTasksId = openTasksId,
    )
}