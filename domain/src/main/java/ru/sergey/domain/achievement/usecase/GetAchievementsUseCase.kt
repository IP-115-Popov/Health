package ru.sergey.domain.achievement.usecase

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.repository.AchievementRepository

class GetAchievementsUseCase(private val achievementRepository: AchievementRepository) {
    operator suspend fun invoke(): Flow<List<Achievement>> = achievementRepository.getAchievements()
}