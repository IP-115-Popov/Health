package ru.sergey.domain.achievement.repository

import kotlinx.coroutines.flow.Flow
import ru.sergey.domain.achievement.models.Achievement

interface AchievementRepository {
    fun getAchievements(): Flow<List<Achievement>>
}