package ru.sergey.health.feature.achievement.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sergey.domain.achievement.models.Achievement
import ru.sergey.domain.achievement.models.AchievementContext
import ru.sergey.health.ui.theme.ui.HealthTheme

@Composable
fun AchievementUiModel(achievement: Achievement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = if(achievement.isUnlocked)  HealthTheme.colors.green else HealthTheme.colors.card, shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = achievement.title)
            Text(text = achievement.description)
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                imageVector = if (achievement.isUnlocked) {
                    Icons.Filled.Done
                } else {
                    Icons.Filled.Clear
                }, contentDescription = null
            )
            Row {
                Text(text = "Прогресс ")
                Text(text = achievement.progress.toString() + "/" + achievement.progressMaxValue)
            }

        }
    }
}

@Preview
@Composable
fun AchievementUiModelPreview() {
    HealthTheme {
        Column {
            AchievementUiModel(
                achievement = Achievement(
                    id = 0,
                    title = "title",
                    description = "title",
                    isUnlocked = false,
                    context = AchievementContext.TotalPoints(pointsRequired = 2),
                    progress = 10,
                    progressMaxValue = 10,
                )
            )
            AchievementUiModel(
                achievement = Achievement(
                    id = 0,
                    title = "title",
                    description = "title",
                    isUnlocked = true,
                    context = AchievementContext.TotalPoints(pointsRequired = 2),
                    progress = 10,
                    progressMaxValue = 10,
                )
            )
        }

    }
}
