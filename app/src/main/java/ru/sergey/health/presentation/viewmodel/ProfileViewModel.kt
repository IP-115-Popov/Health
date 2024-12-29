package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.domain.models.Player
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor()
    : ViewModel()
{
    private val _state = MutableStateFlow(ProfileUiState(Player(
        name = "abober",
        avatar = "a[sfl[al",
        level = 5,
        ex = 1009,
        closeTasksId = listOf(1,2,3),
        openTasksId = listOf(1,2,3)
    )))
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()
}