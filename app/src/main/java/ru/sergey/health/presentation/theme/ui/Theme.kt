package ru.sergey.health.presentation.theme.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun HealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colors = when (darkTheme) {
        true -> {
            baseDarkPalette
        }

        false -> {
            baseDarkPalette
        }
    }

    val typography = SimpleTypography

    CompositionLocalProvider(
        LocalHealthColors provides colors,
        LocalHealthTypography provides typography,
        content = content
    )
}