package ru.sergey.health.feature.achievement.vm

import ru.sergey.domain.achievement.models.Achievement

data class AchievementState(
    val achievements: List<Achievement> = emptyList(),
)