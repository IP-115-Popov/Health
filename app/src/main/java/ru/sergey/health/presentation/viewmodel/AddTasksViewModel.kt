package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sergey.domain.UseCase.AddTaskUseCase
import ru.sergey.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class AddTasksViewModel  @Inject constructor(
    val addTaskUseCase: AddTaskUseCase
)
    : ViewModel() {
        fun addTask(titleText : String, descriptionText : String, targetPointsText : String) {
            addTaskUseCase.exectute(
                Task(
                    id = 0,
                    title = titleText,
                    description = descriptionText,
                    points = 0,
                    targetPoints = targetPointsText.toInt()
                ))
        }
}