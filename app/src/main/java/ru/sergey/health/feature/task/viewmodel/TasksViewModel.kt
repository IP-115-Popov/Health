package ru.sergey.health.feature.task.viewmodel

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
import ru.sergey.domain.task.usecase.DeleteTaskUseCase
import ru.sergey.domain.task.usecase.DownloadTasksUseCase
import ru.sergey.domain.task.usecase.UpdateTaskUseCase
import ru.sergey.domain.task.models.Task
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val downloadTasksUseCase: DownloadTasksUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {

    private val _tasksUiState = MutableStateFlow(TasksUIState())
    val tasksUiState: StateFlow<TasksUIState> = _tasksUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTasksUseCase.execute().onEach { tasks: List<Task> ->
                withContext(Dispatchers.Main) {
                    _tasksUiState.update {
                        it.copy(tasks = tasks)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun addPointsToTask(id: Int) {
        var updateTask = tasksUiState.value.tasks.find { it.id == id }

        if (updateTask != null) {
            val updatedPoints: Int =
                if (updateTask.points < updateTask.targetPoints) updateTask.points + 1
                else updateTask.points
            updateTask = updateTask.newBuilder().setPoints(updatedPoints).build()

            viewModelScope.launch(Dispatchers.IO) {
                updateTaskUseCase.execute(updateTask)
            }
        }
    }

    fun deleteTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase.execute(id)
        }
    }
}