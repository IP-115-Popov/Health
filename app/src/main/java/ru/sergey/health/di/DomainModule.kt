package ru.sergey.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.sergey.domain.UseCase.AddTaskUseCase
import ru.sergey.domain.UseCase.DeleteTaskUseCase
import ru.sergey.domain.UseCase.DownloadTasksUseCase
import ru.sergey.domain.UseCase.GetTaskUseCase
import ru.sergey.domain.UseCase.UpdateTaskUseCase
import ru.sergey.domain.repository.TasksRepository

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideAddTaskUseCase(tasksRepository: TasksRepository) : AddTaskUseCase {
        return AddTaskUseCase(tasksRepository = tasksRepository)
    }
    @Provides
    fun provideDownloadTasksUseCase(tasksRepository: TasksRepository) : DownloadTasksUseCase {
        return DownloadTasksUseCase(tasksRepository = tasksRepository)
    }
    @Provides
    fun provideUpdateTaskUseCase(tasksRepository: TasksRepository) : UpdateTaskUseCase {
        return UpdateTaskUseCase(tasksRepository = tasksRepository)
    }
    @Provides
    fun provideGetTaskUseCase(tasksRepository: TasksRepository) : GetTaskUseCase {
        return GetTaskUseCase(tasksRepository = tasksRepository)
    }
    @Provides
    fun provideDeleteTaskUseCase(tasksRepository: TasksRepository) : DeleteTaskUseCase {
        return DeleteTaskUseCase(tasksRepository = tasksRepository)
    }
}