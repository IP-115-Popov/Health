package ru.sergey.health.feature.achievement.vm

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
import ru.sergey.domain.achievement.models.AchievementContext
import ru.sergey.domain.achievement.usecase.GetAchievementsUseCase
import ru.sergey.domain.task.models.Task
import ru.sergey.domain.task.usecase.DownloadTasksUseCase
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    val downloadTasksUseCase: DownloadTasksUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementState())
    val state: StateFlow<AchievementState> = _state.asStateFlow()

    init {
        getAchievement()
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTasksUseCase.execute().onEach { tasks: List<Task> ->
                _state.value.achievements.forEach { achievement ->
                    when (val con = achievement.context) {
                        is AchievementContext.PointsOnTime -> {

                        }

                        is AchievementContext.StreakDays -> {

                        }

                        is AchievementContext.TotalPoints -> {
                            val totalPoints = tasks.sumOf { it.points }
                            val pointsRequired = con.pointsRequired
                            withContext(Dispatchers.Main.immediate) {
                                _state.update {
                                    it.copy(achievements = it.achievements.map {
                                        if (it.id == achievement.id) {
                                            achievement.copy(
                                                progress = if (totalPoints < pointsRequired) totalPoints else pointsRequired,
                                                isUnlocked = totalPoints == pointsRequired
                                            )
                                        } else it
                                    })
                                }
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
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