package ru.sergey.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.sergey.domain.gamecontroller.GameControllerRepository
import ru.sergey.domain.gamecontroller.SetGameControllerUseCase

@Module
@InstallIn(ViewModelComponent::class)
class SetGameControllerUseCaseModule {
    @Provides
    fun provideSetGameControllerUseCase(gameControllerRepository: GameControllerRepository) : SetGameControllerUseCase {
        return SetGameControllerUseCase(gameControllerRepository = gameControllerRepository)
    }
}