package ru.sergey.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.gamecontroller.GameControllerRepositoryImpl
import ru.sergey.domain.gamecontroller.GameControllerRepository

@Module
@InstallIn(SingletonComponent::class)
interface GameControllerRepositoryModule {
    @Binds
    fun bindGameControllerRepository(impl: GameControllerRepositoryImpl): GameControllerRepository
}