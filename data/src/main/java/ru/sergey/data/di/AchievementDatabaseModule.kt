package ru.sergey.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.achievement.db.AchievementDao
import ru.sergey.data.achievement.db.AchievementDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AchievementDatabaseModule {

    @Provides
    @Singleton
    fun provideAchievementDatabase(application: Application): AchievementDatabase {
        return Room.databaseBuilder(
            application,
            AchievementDatabase::class.java,
            AchievementDatabase.DATABASE_NAME
        )
            .createFromAsset("db/initAchievement.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideAchievementDao(achievementDatabase: AchievementDatabase): AchievementDao {
        return achievementDatabase.AchievementDao()
    }
}