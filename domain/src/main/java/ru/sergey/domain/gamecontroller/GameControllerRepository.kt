package ru.sergey.domain.gamecontroller

import kotlinx.coroutines.flow.Flow

interface GameControllerRepository {
    suspend fun saveGameController(gameController: GameController)
    val gameControllerFlow: Flow<GameController>
}