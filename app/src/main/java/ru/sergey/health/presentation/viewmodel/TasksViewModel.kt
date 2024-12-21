package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sergey.domain.UseCase.DownloadTasksUseCase
import ru.sergey.domain.UseCase.UpdateTaskUseCase
import ru.sergey.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val downloadTasksUseCase: DownloadTasksUseCase,
    val updateTaskUseCase : UpdateTaskUseCase
) : ViewModel() {

    private val _tasksUiState = MutableStateFlow(TasksUIState())
    val tasksUiState: StateFlow<TasksUIState> = _tasksUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTasksUseCase.exectute().onEach { tasks: List<Task> ->
                withContext(Dispatchers.Main) {
                    _tasksUiState.update {
                        it.copy(tasks = tasks)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
//    fun addPointsToTask(id : Int) {
//        var updateTask = tasks.find{it.id == id}
//
//        if (updateTask != null)
//        {
//            updateTask = updateTask.newBuilder().setPoints(updateTask.points + 1).build()
//
//            viewModelScope.launch(Dispatchers.IO) {
//                updateTaskUseCase.exectute(updateTask)
//            }
//        }
//    }
}