package ru.sergey.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.profile.repository.AvatarRepositoryImp
import ru.sergey.data.profile.repository.ProfileRepositoryImp
import ru.sergey.data.task.db.TaskDao
import ru.sergey.data.task.db.TaskRoomDatabase
import ru.sergey.data.task.repository.TaskRepositoryImp
import ru.sergey.domain.profile.repository.TasksRepository
import ru.sergey.domain.task.repository.AvatarRepository
import ru.sergey.domain.task.repository.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideTasksRepository(taskDao: TaskDao): TasksRepository {
        return TaskRepositoryImp(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskDao(@ApplicationContext context: Context): TaskDao {
        return TaskRoomDatabase.getInstance(context = context).TaskDao()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(@ApplicationContext context: Context): ProfileRepository {
        return ProfileRepositoryImp(context)
    }

    @Provides
    @Singleton
    fun provideAvatarRepository(@ApplicationContext context: Context): AvatarRepository {
        return AvatarRepositoryImp(context)
    }
}