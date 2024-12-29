package ru.sergey.health.presentation.viewmodel

import ru.sergey.domain.models.Player

data class ProfileUiState(
    val player: Player = Player()
)