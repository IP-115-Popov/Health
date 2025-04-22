package ru.sergey.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.sergey.domain.gamecontroller.GameControllerRepository
import ru.sergey.domain.task.usecase.AddTaskUseCase
import ru.sergey.domain.task.usecase.DeleteTaskUseCase
import ru.sergey.domain.task.usecase.DownloadTasksUseCase
import ru.sergey.domain.profile.usecase.GetAvatarUseCase
import ru.sergey.domain.task.usecase.GetPointsUseCase
import ru.sergey.domain.profile.usecase.GetProfileUseCase
import ru.sergey.domain.task.usecase.GetTaskUseCase
import ru.sergey.domain.profile.usecase.SaveAvatarUseCase
import ru.sergey.domain.profile.usecase.SaveProfileUseCase
import ru.sergey.domain.task.usecase.UpdateTaskUseCase
import ru.sergey.domain.task.repository.AvatarRepository
import ru.sergey.domain.task.repository.ProfileRepository
import ru.sergey.domain.profile.repository.TasksRepository

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideAddTaskUseCase(tasksRepository: TasksRepository): AddTaskUseCase {
        return AddTaskUseCase(tasksRepository = tasksRepository)
    }

    @Provides
    fun provideDownloadTasksUseCase(tasksRepository: TasksRepository): DownloadTasksUseCase {
        return DownloadTasksUseCase(tasksRepository = tasksRepository)
    }

    @Provides
    fun provideGetTaskUseCase(tasksRepository: TasksRepository): GetTaskUseCase {
        return GetTaskUseCase(tasksRepository = tasksRepository)
    }

    @Provides
    fun provideDeleteTaskUseCase(tasksRepository: TasksRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(tasksRepository = tasksRepository)
    }

    @Provides
    fun provideUpdateTaskUseCase(tasksRepository: TasksRepository, gameControllerRepository: GameControllerRepository): UpdateTaskUseCase {
        return UpdateTaskUseCase(tasksRepository = tasksRepository, gameControllerRepository = gameControllerRepository)
    }

    @Provides
    fun provideGetPointsUseCase(tasksRepository: TasksRepository): GetPointsUseCase {
        return GetPointsUseCase(tasksRepository = tasksRepository)
    }

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(profileRepository = profileRepository)
    }

    @Provides
    fun provideSaveProfileUseCase(profileRepository: ProfileRepository): SaveProfileUseCase {
        return SaveProfileUseCase(profileRepository = profileRepository)
    }

    @Provides
    fun provideSaveAvatarUseCase(avatarRepository: AvatarRepository): SaveAvatarUseCase {
        return SaveAvatarUseCase(avatarRepository = avatarRepository)
    }

    @Provides
    fun provideGetAvatarUseCase(avatarRepository: AvatarRepository): GetAvatarUseCase {
        return GetAvatarUseCase(avatarRepository = avatarRepository)
    }
}