package ru.sergey.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.sergey.domain.achievement.repository.AchievementRepository
import ru.sergey.domain.achievement.usecase.UpdateAchievementUseCase

@InstallIn(ViewModelComponent::class)
@Module
class UpdateAchievementUseCaseModule {
    @Provides
    fun provideUpdateAchievementUseCase(achievementRepository: AchievementRepository) = UpdateAchievementUseCase(achievementRepository = achievementRepository)
}