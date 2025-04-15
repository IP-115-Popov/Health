package ru.sergey.health.feature.achievement.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sergey.domain.achievement.usecase.GetAchievementsUseCase
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val getAchievementsUseCase: GetAchievementsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementState())
    val state: StateFlow<AchievementState> = _state.asStateFlow()

    init {
        getAchievement()
    }

    private fun getAchievement() {
        viewModelScope.launch(Dispatchers.IO) {
            getAchievementsUseCase().collect { updated ->
                _state.update {
                    it.copy(
                        achievements = updated
                    )
                }
            }
        }
    }
}