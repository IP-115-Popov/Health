package ru.sergey.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.repository.ProfileRepositoryImp
import ru.sergey.data.repository.TaskRepositoryImp
import ru.sergey.data.storage.TaskDao
import ru.sergey.data.storage.TaskRoomDatabase
import ru.sergey.domain.repository.ProfileRepository
import ru.sergey.domain.repository.TasksRepository
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
}