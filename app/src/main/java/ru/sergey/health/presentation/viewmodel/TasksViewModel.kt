package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sergey.domain.UseCase.DownloadTasksUseCase
import ru.sergey.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val downloadTasksUseCase: DownloadTasksUseCase
)
    : ViewModel() {
        val tasks = mutableListOf<Task>()
        fun updateTasks() {
            tasks.clear()
            tasks.addAll(downloadTasksUseCase.exectute())
        }
}