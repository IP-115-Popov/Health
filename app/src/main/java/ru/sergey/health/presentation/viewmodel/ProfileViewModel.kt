package ru.sergey.health.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sergey.domain.UseCase.GetProfileUseCase
import ru.sergey.domain.UseCase.SaveProfileUseCase
import ru.sergey.domain.models.Player
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getProfileUseCase: GetProfileUseCase,
    val saveProfileUseCase: SaveProfileUseCase
)
    : ViewModel()
{
    private val _state = MutableStateFlow(ProfileUiState(Player()))
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getProfileUseCase.execute().onEach { player: Player ->
                _state.update { it ->
                    it.copy(player = player)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun savePlayer() {
        viewModelScope.launch {
            saveProfileUseCase.execute(state.value.player)
        }
    }

    fun setName(value: String) {
        _state.update {
            it.copy(player = it.player.copy(name = value))
        }
    }


    override fun onCleared() {
        savePlayer()
    }
}