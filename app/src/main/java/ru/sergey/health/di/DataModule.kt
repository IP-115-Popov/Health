package ru.sergey.health.di
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.repository.TaskRepositoryImp
import ru.sergey.domain.repository.TasksRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideTasksRepository(@ApplicationContext context: Context) : TasksRepository {
        return TaskRepositoryImp(context = context)
    }
}