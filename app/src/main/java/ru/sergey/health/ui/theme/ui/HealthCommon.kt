package ru.sergey.health.ui.theme.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class HealthColors(
    val background: Color,
    val iconColor: Color,
    val primary: Color,
    val placeholderText: Color,
    val text: Color,
    val card: Color,
    val green: Color,
)

data class HealthTypography(
    val h1: TextStyle,
    val body1: TextStyle,
    val button: TextStyle,
    val navigation: TextStyle
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

