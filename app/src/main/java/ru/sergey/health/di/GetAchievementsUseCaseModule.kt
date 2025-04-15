package ru.sergey.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.sergey.domain.achievement.repository.AchievementRepository
import ru.sergey.domain.achievement.usecase.GetAchievementsUseCase

@Module
@InstallIn(ViewModelComponent::class)
class GetAchievementsUseCaseModule {
    @Provides
    fun provideGetAchievementsUseCase(achievementRepository: AchievementRepository): GetAchievementsUseCase {
        return GetAchievementsUseCase(achievementRepository = achievementRepository)
    }
}