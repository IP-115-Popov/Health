package ru.sergey.health.feature.newtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sergey.domain.UseCase.AddTaskUseCase
import ru.sergey.domain.UseCase.GetTaskUseCase
import ru.sergey.domain.UseCase.UpdateTaskUseCase
import ru.sergey.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class AddTasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    private val _tasksUiState = MutableStateFlow(AddTaskUIState())
    val tasksUiState: StateFlow<AddTaskUIState> = _tasksUiState.asStateFlow()


    fun getTask(id: Int) {
        if (id != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                val updatedTask = getTaskUseCase.execute(id)
                if (updatedTask != null) {
                    withContext(Dispatchers.Main) {
                        _tasksUiState.update {
                            it.copy(
                                id = updatedTask.id,
                                titleText = updatedTask.title,
                                descriptionText = updatedTask.description,
                                points = updatedTask.points,
                                targetPointsText = updatedTask.targetPoints,
                                measureUnitText = updatedTask.measureUnit,
                            )
                        }
                    }
                }
            }
        } else {
            _tasksUiState.update {
                it.copy(
                    id = 0,
                    titleText = "",
                    descriptionText = "",
                    points = 0,
                    targetPointsText = 0,
                    measureUnitText = "",
                )
            }
        }
    }

    // Обновляем отдельные поля
    fun updateTitle(title: String) {
        _tasksUiState.update { it.copy(titleText = title) }
    }

    fun updateDescription(description: String) {
        _tasksUiState.update { it.copy(descriptionText = description) }
    }

    fun updateTargetPoints(targetPoints: String) {
        _tasksUiState.update { it.copy(targetPointsText = targetPoints.toIntOrNull() ?: 0) }
    }

    fun updateMeasureUnit(measureUnit: String) {
        _tasksUiState.update { it.copy(measureUnitText = measureUnit) }
    }

    // Добавление задачи
    fun addTask() {
        val state = _tasksUiState.value
        if (state.titleText.isNotBlank() && state.descriptionText.isNotBlank() && state.targetPointsText > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                addTaskUseCase.execute(
                    Task(
                        id = state.id,
                        title = state.titleText,
                        description = state.descriptionText,
                        points = state.points,
                        targetPoints = state.targetPointsText,
                        measureUnit = state.measureUnitText
                    )
                )
            }
        }
    }

    fun updateTask() {
        val state = _tasksUiState.value
        if (state.titleText.isNotBlank() && state.descriptionText.isNotBlank() && state.targetPointsText > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                updateTaskUseCase.execute(
                    Task(
                        id = state.id,
                        title = state.titleText,
                        description = state.descriptionText,
                        points = state.points,
                        targetPoints = state.targetPointsText,
                        measureUnit = state.measureUnitText
                    )
                )
            }
        }
    }

    fun updatePoints(points: String) {
        _tasksUiState.update { it.copy(points = points.toIntOrNull() ?: 0) }
    }
}