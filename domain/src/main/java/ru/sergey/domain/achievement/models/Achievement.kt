package ru.sergey.domain.achievement.models

data class Achievement(
    val id: Long,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val context: AchievementContext,
    val progress: Int = 0, // Текущий прогресс достижения (например, количество очков)
    val progressMaxValue: Int, // Максимальное значение прогресса (для отображения прогресса)
)

sealed class AchievementContext {
    data class PointsOnTime(val pointsRequired: Int, val timePeriodDays: Int) : AchievementContext()
    data class TotalPoints(val pointsRequired: Int) : AchievementContext()
    data class StreakDays(val daysRequired: Int) : AchievementContext()
}
