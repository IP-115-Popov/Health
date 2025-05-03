package ru.sergey.data.achievement.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.sergey.data.achievement.model.AchievementEntity.Companion.TABLE_NAME
import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.models.AchievementContext

@Entity(tableName = TABLE_NAME)
data class AchievementEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val progress: Int = 0,
    val progressMaxValue: Int,
    val exp: Int = 0,

    // Данные для TotalPoints
    val totalPoints_pointsRequired: Int? = null,

    // Данные для StreakDays
    val streakDays_daysRequired: Int? = null,

) {
    companion object {
        const val TABLE_NAME = "achievement"
        fun toEntity(achievement: Achievement): AchievementEntity = with(achievement) {
            val res = AchievementEntity(
                id = id,
                title = title,
                description = description,
                isUnlocked = isUnlocked,
                progress = progress,
                progressMaxValue = progressMaxValue,
                exp = exp
            )
            when (val con = context) {
                is AchievementContext.StreakDays -> {
                    res.copy(
                        totalPoints_pointsRequired = con.daysRequired
                    )
                }
                is AchievementContext.TotalPoints -> {
                    res.copy(
                        streakDays_daysRequired = con.pointsRequired
                    )
                }
            }
        }
    }
    fun fromEntity(): Achievement {
        val context = when {
            totalPoints_pointsRequired != null -> {
                AchievementContext.TotalPoints(
                    pointsRequired = totalPoints_pointsRequired
                )
            }
            streakDays_daysRequired != null -> {
                AchievementContext.StreakDays(
                    daysRequired = streakDays_daysRequired
                )
            }
            else -> throw IllegalStateException("Unknown AchievementContext") // Или верните null, если возможно
        }
        return Achievement(
            id = id,
            title = title,
            description = description,
            isUnlocked = isUnlocked,
            progress = progress,
            progressMaxValue = progressMaxValue,
            exp = exp,
            context = context,
        )
    }
}