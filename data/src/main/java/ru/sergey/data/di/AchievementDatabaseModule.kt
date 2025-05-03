package ru.sergey.data.di

import android.app.Application
import android.util.Log
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
        val dbPath = "db/init4.db"
        try {
            // Проверяем, существует ли файл в assets
            val assetManager = application.assets
            try {
                assetManager.open(dbPath).close() // Попытка открыть файл
                Log.i("provideAchievementDatabase", "Файл $dbPath найден в assets")
            } catch (e: Exception) {
                Log.e("provideAchievementDatabase", "Файл $dbPath не найден в assets: ${e.message}")
                error("Файл базы данных не найден в assets")
            }


            Log.i("provideAchievementDatabase", "ok")
            val bd = Room.databaseBuilder(
                application,
                AchievementDatabase::class.java,
                AchievementDatabase.DATABASE_NAME
            )
                .createFromAsset(dbPath)
                .fallbackToDestructiveMigration()
                .build()


            return bd
        } catch (e: Exception) {
            Log.e("provideAchievementDatabase", "${e.message}")
            error("databaseBuilder")
        }
    }


    @Provides
    fun provideAchievementDao(achievementDatabase: AchievementDatabase): AchievementDao {
        return achievementDatabase.AchievementDao()
    }
}