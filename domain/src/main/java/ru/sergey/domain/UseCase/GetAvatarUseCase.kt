package ru.sergey.domain.UseCase

import ru.sergey.domain.repository.AvatarRepository

class GetAvatarUseCase(private val avatarRepository: AvatarRepository) {
    fun execute(): ByteArray
    {
        return avatarRepository.get()
    }
}