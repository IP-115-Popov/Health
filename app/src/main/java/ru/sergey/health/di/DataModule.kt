package ru.sergey.health.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sergey.data.repository.NewsRepositoryImp
import ru.sergey.domain.repository.TasksRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideTasksRepository() : TasksRepository {
        return NewsRepositoryImp()
    }
}