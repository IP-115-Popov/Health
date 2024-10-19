package ru.sergey.health.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sergey.domain.UseCase.DownloadTasksUseCase
import ru.sergey.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val downloadTasksUseCase: DownloadTasksUseCase
) : ViewModel() {

    val tasks = mutableStateListOf<Task>()

    init {
        updateTasks()
    }


    fun updateTasks() {

        viewModelScope.launch(Dispatchers.IO) {
            val tasksDownloaded: List<Task> = downloadTasksUseCase.exectute()
            launch(Dispatchers.Main) { // Обновление UI в главном потоке
                tasks.clear()
                tasks.addAll(tasksDownloaded)
            }
        }

    }
}