package ru.sergey.domain.gamecontroller

class SetGameControllerUseCase(private val gameControllerRepository: GameControllerRepository) {
    operator suspend fun invoke(gameController: GameController) = gameControllerRepository.saveGameController(gameController)
}