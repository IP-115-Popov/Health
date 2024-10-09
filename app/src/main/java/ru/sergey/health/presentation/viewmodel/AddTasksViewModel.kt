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
        fun addTask(task : Task) {
            addTaskUseCase.exectute(task)
        }
}