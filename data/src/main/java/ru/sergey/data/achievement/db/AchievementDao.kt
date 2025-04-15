package ru.sergey.data.achievement.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.sergey.data.achievement.model.AchievementEntity

@Dao
interface AchievementDao {
    @Query("SELECT * FROM ${AchievementEntity.TABLE_NAME}")
    fun getAchievements(): Flow<List<AchievementEntity>>
//    @Query("UPDATE ${AchievementEntity.TABLE_NAME} SET isUnlocked = 1 WHERE id = :id")
//    suspend fun setUnlockedAchievement(id: Long)
}