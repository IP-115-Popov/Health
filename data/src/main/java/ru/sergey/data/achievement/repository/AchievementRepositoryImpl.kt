package ru.sergey.data.achievement.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.sergey.data.achievement.db.AchievementDao
import ru.sergey.data.achievement.model.AchievementEntity
import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.repository.AchievementRepository
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor (private val achievementDao: AchievementDao) : AchievementRepository {
    override suspend fun getAchievements(): Flow<List<Achievement>> =
        achievementDao.getAchievements()
            .map { achievementEntities ->
                achievementEntities.mapNotNull { entity -> // mapNotNull, чтобы фильтровать null
                    try {
                        entity.fromEntity()
                    } catch (e: Exception) {
                        Log.e("AchievementRepository", "Error converting entity to Achievement: ${e.message}")
                        null
                    }
                }
            }
            .catch { e ->
                Log.e("AchievementRepository", "Error fetching achievements: ${e.message}")
                emit(emptyList())
            }
}