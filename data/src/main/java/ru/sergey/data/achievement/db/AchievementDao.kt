package ru.sergey.data.achievement.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.sergey.data.achievement.model.AchievementEntity

@Dao
interface AchievementDao {
    @Query("SELECT * FROM ${AchievementEntity.TABLE_NAME}")
    fun getAchievements(): Flow<List<AchievementEntity>>
    @Update
    fun updateAchievement(achievementEntity: AchievementEntity)
}