package ru.sergey.domain.UseCase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.models.Player
import ru.sergey.domain.repository.ProfileRepository

class GetProfileUseCase(val profileRepository: ProfileRepository)  {
    suspend fun execute() : Flow<Player>
    {
        return profileRepository.get()
    }
}