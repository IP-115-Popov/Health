package ru.sergey.domain.profile.usecase

import ru.sergey.domain.task.repository.AvatarRepository

class GetAvatarUseCase(private val avatarRepository: AvatarRepository) {
    fun execute(): ByteArray {
        return avatarRepository.get()
    }
}