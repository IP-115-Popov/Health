package ru.sergey.domain.profile.usecase

import ru.sergey.domain.profile.models.Player
import ru.sergey.domain.task.repository.ProfileRepository

class SaveProfileUseCase(val profileRepository: ProfileRepository) {
    suspend fun execute(player: Player) {
        profileRepository.save(player)
    }
}