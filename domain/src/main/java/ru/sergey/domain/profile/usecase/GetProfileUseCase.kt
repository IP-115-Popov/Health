package ru.sergey.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.profile.models.Player
import ru.sergey.domain.task.repository.ProfileRepository

class GetProfileUseCase(val profileRepository: ProfileRepository) {
    suspend fun execute(): Flow<Player> {
        return profileRepository.get()
    }
}