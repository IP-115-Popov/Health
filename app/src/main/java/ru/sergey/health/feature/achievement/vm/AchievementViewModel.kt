package ru.sergey.health.feature.achievement.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.models.AchievementContext
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        AchievementState(
            achievements = listOf(
                Achievement(
                    id = 0,
                    title = "title",
                    description = "title",
                    isUnlocked = false,
                    context = AchievementContext.TotalPoints(pointsRequired = 2),
                    progress = 0,
                    progressMaxValue = 10,
                )
            )
        )
    )
    val state: StateFlow<AchievementState> = _state.asStateFlow()
}