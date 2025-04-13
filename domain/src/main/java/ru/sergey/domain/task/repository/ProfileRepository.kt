package ru.sergey.domain.task.repository

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.profile.models.Player

interface ProfileRepository {
    suspend fun save(player: Player)
    suspend fun get(): Flow<Player>
}