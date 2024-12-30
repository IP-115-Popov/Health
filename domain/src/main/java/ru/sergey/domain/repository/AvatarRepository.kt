package ru.sergey.domain.repository

interface AvatarRepository {
    fun save(byteArray: ByteArray)
    fun get(): ByteArray
}