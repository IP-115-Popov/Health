package ru.sergey.domain.achievement.usecase

import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.repository.AchievementRepository

class UpdateAchievementUseCase(private val achievementRepository: AchievementRepository) {
    operator suspend fun invoke(achievement: Achievement) = achievementRepository.updateAchievement(achievement = achievement)
}