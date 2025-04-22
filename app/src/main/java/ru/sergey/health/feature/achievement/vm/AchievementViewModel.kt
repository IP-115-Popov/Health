package ru.sergey.health.feature.achievement.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sergey.domain.achievement.models.AchievementContext
import ru.sergey.domain.achievement.usecase.GetAchievementsUseCase
import ru.sergey.domain.gamecontroller.GetGameControllerUseCase
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val getGameControllerUseCase: GetGameControllerUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementState())
    val state: StateFlow<AchievementState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getAchievementsUseCase(),
                getGameControllerUseCase()
            ) { achievements, gameController ->
                achievements.map { achievement ->
                    when (val con = achievement.context) {
                        is AchievementContext.PointsOnTime -> achievement

                        is AchievementContext.StreakDays -> {
                            val daysRequired = con.daysRequired
                            if (!achievement.isUnlocked) {
                                achievement.copy(
                                    isUnlocked = gameController.streakDays >= daysRequired,
                                    progress = gameController.streakDays
                                )
                            } else achievement
                        }

                        is AchievementContext.TotalPoints -> {
                            val pointsRequired = con.pointsRequired
                            if (!achievement.isUnlocked) {
                                achievement.copy(
                                    isUnlocked = gameController.totalPoints >= pointsRequired,
                                    progress = gameController.totalPoints
                                )
                            } else achievement
                        }
                    }
                }
            }.catch { e ->
                Log.e("AchievementViewModel","Error during achievements update: ${e.message}")
            }.collect { updatedAchievements ->
                _state.update {
                    it.copy(achievements = updatedAchievements)
                }
            }
        }
    }
}