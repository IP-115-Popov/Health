package ru.sergey.domain.UseCase

import ru.sergey.domain.repository.AvatarRepository

class SaveAvatarUseCase(private val avatarRepository: AvatarRepository) {
    fun execute(byteArray: ByteArray)
    {
        return avatarRepository.save(byteArray)
    }
}