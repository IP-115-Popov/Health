package ru.sergey.domain.profile.usecase

import ru.sergey.domain.task.repository.AvatarRepository

class SaveAvatarUseCase(private val avatarRepository: AvatarRepository) {
    fun execute(byteArray: ByteArray) {
        return avatarRepository.save(byteArray)
    }
}