package ru.sergey.health.presentation.theme.ui

import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.sergey.health.R


val SimpleTypography = HealthTypography(
    h1 = TextStyle(
        color = White,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight(500),
        letterSpacing = 0.15.sp,
        lineHeight = 22.sp,
    )
)