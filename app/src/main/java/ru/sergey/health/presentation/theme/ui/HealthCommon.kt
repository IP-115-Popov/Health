package ru.sergey.health.presentation.theme.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class HealthColors(
    val primaryBackground: Color,
    val iconColor: Color,
    val topBarContainerColor: Color,
    val titleContentColor: Color,
    val cardColor: Color
)

data class HealthTypography(
    val h1: TextStyle
)

object HealthTheme {
    val colors: HealthColors
        @Composable get() = LocalHealthColors.current

    val typography: HealthTypography
        @Composable get() = LocalHealthTypography.current
}

val LocalHealthColors = staticCompositionLocalOf<HealthColors> {
    error("No colors provided")
}
val LocalHealthTypography = staticCompositionLocalOf<HealthTypography> {
    error("No typography provided")
}

