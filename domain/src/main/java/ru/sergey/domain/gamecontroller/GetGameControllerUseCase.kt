package ru.sergey.domain.gamecontroller

import kotlinx.coroutines.flow.Flow

class GetGameControllerUseCase(private val gameControllerRepository: GameControllerRepository) {
    operator fun invoke(): Flow<GameController> = gameControllerRepository.gameControllerFlow
}