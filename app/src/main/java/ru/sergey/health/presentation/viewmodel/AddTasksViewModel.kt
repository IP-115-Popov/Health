package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sergey.domain.UseCase.AddTaskUseCase
import ru.sergey.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class AddTasksViewModel  @Inject constructor(
    val addTaskUseCase: AddTaskUseCase
)
    : ViewModel() {

        fun addTask(titleText : String, descriptionText : String, targetPointsText : String) {
            viewModelScope.launch(Dispatchers.IO) {
                addTaskUseCase.exectute(
                    Task(
                        id = 0,
                        title = titleText,
                        description = descriptionText,
                        points = 0,
                        targetPoints = targetPointsText.toInt()
                    )
                )
            }
        }

}