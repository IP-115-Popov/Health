package ru.sergey.domain.task.repository

interface AvatarRepository {
    fun save(byteArray: ByteArray)
    fun get(): ByteArray
}