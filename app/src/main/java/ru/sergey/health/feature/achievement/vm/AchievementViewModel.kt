package ru.sergey.health.feature.achievement.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.models.AchievementContext
import ru.sergey.domain.achievement.usecase.GetAchievementsUseCase
import ru.sergey.domain.achievement.usecase.UpdateAchievementUseCase
import ru.sergey.domain.gamecontroller.GameController
import ru.sergey.domain.gamecontroller.GetGameControllerUseCase
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val getGameControllerUseCase: GetGameControllerUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val updateAchievementUseCase: UpdateAchievementUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementState())
    val state: StateFlow<AchievementState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getGameControllerUseCase().map { gameController ->
                val achievements = getAchievementsUseCase().first()
                achievements.map { achievement ->
                    updateAchievement(achievement, gameController)
                }
            }.map {
                it.sortedBy { !it.isUnlocked }
            }.catch { e ->
                Log.e("AchievementViewModel","Error during achievements update: ${e.message}")
            }.collect { updatedAchievements ->
                withContext(Dispatchers.Main.immediate) {
                    _state.update {
                        it.copy(achievements = updatedAchievements)
                    }
                }
                saveAchievements(this, updatedAchievements, this@AchievementViewModel)
            }
        }
    }

    private fun updateAchievement(
        achievement: Achievement,
        gameController: GameController
    ): Achievement = if (achievement.isUnlocked) {
        achievement.copy(progress = achievement.progressMaxValue)
    } else {
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

    private fun saveAchievements(
        coroutineScope: CoroutineScope,
        updatedAchievements: List<Achievement>,
        achievementViewModel: AchievementViewModel
    ) {
        coroutineScope.launch {
            updatedAchievements.forEach {
                achievementViewModel.updateAchievementUseCase(
                    it.copy(
                        isUnlocked = (it.progress >= it.progressMaxValue)
                    )
                )
            }
        }
    }
}