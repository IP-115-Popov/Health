package ru.sergey.health.feature.profile.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import ru.sergey.domain.profile.models.Player

data class ProfileUiState(
    val player: Player = Player(),
    val imgAvatar: ImageBitmap? = null
)