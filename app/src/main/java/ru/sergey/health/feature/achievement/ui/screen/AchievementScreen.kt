package ru.sergey.health.feature.achievement.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.sergey.health.feature.achievement.ui.components.AchievementTopBar
import ru.sergey.health.feature.achievement.ui.components.AchievementUiModel
import ru.sergey.health.feature.achievement.vm.AchievementViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme

@Composable
fun AchievementScreen(
    navController: NavHostController,
    achievementViewModel: AchievementViewModel,
) {
    val state = achievementViewModel.state.collectAsState()

    Scaffold(
        topBar = { AchievementTopBar(navController) },
        containerColor = HealthTheme.colors.background,
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp)) {
            items(state.value.achievements, key = {it.id}) { achievement ->
                AchievementUiModel(achievement)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



