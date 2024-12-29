package ru.sergey.domain.UseCase

import ru.sergey.domain.models.Player
import ru.sergey.domain.repository.ProfileRepository

class SaveProfileUseCase(val profileRepository: ProfileRepository)  {
    suspend fun execute(player: Player)
    {
        profileRepository.save(player)
    }
}