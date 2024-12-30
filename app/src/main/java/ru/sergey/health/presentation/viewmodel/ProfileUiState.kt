package ru.sergey.health.presentation.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import ru.sergey.domain.models.Player

data class ProfileUiState(
    val player: Player = Player(),
    val imgAvatar: ImageBitmap? = null
)