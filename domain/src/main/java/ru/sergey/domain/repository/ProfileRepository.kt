package ru.sergey.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Player

interface ProfileRepository {
    suspend fun save(player: Player)
    suspend fun get(): Flow<Player>
}