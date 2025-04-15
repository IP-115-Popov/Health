package ru.sergey.data.achievement.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sergey.data.achievement.model.AchievementEntity

@Database(
    entities = [AchievementEntity::class], version = 1
)
abstract class AchievementDatabase: RoomDatabase() {
    abstract fun AchievementDao(): AchievementDao

    companion object {
        const val DATABASE_NAME = "achievement_database_1.db"

        @Volatile
        private var INSTANCE: AchievementDatabase? = null
    }
}