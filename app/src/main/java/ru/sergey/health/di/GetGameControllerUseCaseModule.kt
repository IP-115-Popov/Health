package ru.sergey.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.sergey.domain.gamecontroller.GameControllerRepository
import ru.sergey.domain.gamecontroller.GetGameControllerUseCase

@InstallIn(ViewModelComponent::class)
@Module
class GetGameControllerUseCaseModule {
    @Provides
    fun provideGetGameControllerUseCase(gameControllerRepository: GameControllerRepository): GetGameControllerUseCase {
        return GetGameControllerUseCase(gameControllerRepository = gameControllerRepository)
    }
}