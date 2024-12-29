package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sergey.domain.UseCase.GetPointsUseCase
import javax.inject.Inject

@HiltViewModel
class GraphViewModel@Inject constructor(
    val getPointsUseCase: GetPointsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GraphUiState())
    val state: StateFlow<GraphUiState> = _state.asStateFlow()

    fun loadData(taskId: Int) {
        //_state.update { it.copy(task = task) }

//        _state.update {
//                    it.copy(pointsList = listOf(
//                        Points(0,0,"10.10.2024", 5),
//                        Points(0,0,"11.10.2024", 6),
//                        Points(0,0,"12.10.2024", 7),
//                        Points(0,0,"13.10.2024", 8),
//                        Points(0,0,"14.10.2024", 9),
//                        Points(0,0,"15.10.2024", 10),
//                        Points(0,0,"16.10.2024", 11),
//                        Points(0,0,"17.10.2024", 12),
//                        Points(0,0,"18.10.2024", 13),
//                        Points(0,0,"19.10.2024", 14),
//                        Points(0,0,"20.10.2024", 15),
//                        Points(0,0,"21.10.2024", 16),
//                        Points(0,0,"22.10.2024", 17),
//                    ))
//                }
        viewModelScope.launch(Dispatchers.IO) {
            val paintsList = getPointsUseCase.execute(taskId)
            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(pointsList = paintsList.first())
                }
            }
        }
    }
}