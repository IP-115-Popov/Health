package ru.sergey.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.achievement.repository.AchievementRepositoryImpl
import ru.sergey.domain.achievement.repository.AchievementRepository

@Module
@InstallIn(SingletonComponent::class)
interface AchievementRepositoryModule {
    @Binds
    fun bindAchievementRepository(impl: AchievementRepositoryImpl): AchievementRepository
}