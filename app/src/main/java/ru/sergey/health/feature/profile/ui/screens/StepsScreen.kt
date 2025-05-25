package ru.sergey.health.feature.profile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.sergey.health.R
import ru.sergey.health.feature.graph.ui.screens.PointsGraph
import ru.sergey.health.feature.graph.ui.screens.toMillis
import ru.sergey.health.feature.profile.viewmodel.ProfileViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StepsScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    val state by profileViewModel.state.collectAsState()
    val stepsList = state.stepsList.toList()
    var selectedTabIndex by remember { mutableStateOf(0) } // Состояние для выбранной вкладки

    Scaffold(
        topBar = { StepsTopBar(navController = navController) }
    ) { innerPadding ->

        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(HealthTheme.colors.background)
        ) {
            // TabRow для переключения между экранами
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Шаги") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("График") }
                )
            }
            if (stepsList.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет данных",
                        style = HealthTheme.typography.h1.copy(color = HealthTheme.colors.text)
                    )
                }
            } else {
                // Контент в зависимости от выбранной вкладки
                when (selectedTabIndex) {
                    0 -> {

                        LazyColumn(
                            Modifier
                                .fillMaxSize()
                        ) {
                            items(stepsList.size) { index ->
                                val dateString = stepsList[index]
                                StepStatsItem(dateString.first, dateString.second)
                            }
                        }
                    }

                    1 -> {
                        Box(
                            Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(0.9f)
                                .background(HealthTheme.colors.card)
                        ) {
                            PointsGraph(points = stepsList.map { it.first.toMillis() to it.second.toInt() })
                            Text(
                                text = "time",
                                style = HealthTheme.typography.h1,
                                color = HealthTheme.colors.primary,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            )

                            Text(
                                text = "point",
                                style = HealthTheme.typography.h1,
                                color = HealthTheme.colors.primary,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(16.dp)
                            )

                            Text(
                                text = "0",
                                style = HealthTheme.typography.h1,
                                color = HealthTheme.colors.primary,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsTopBar(
    navController: NavHostController,
) {
    CenterAlignedTopAppBar(title = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = stringResource(R.string.profile),
                style = HealthTheme.typography.h1.copy(color = HealthTheme.colors.text),
                modifier = Modifier.align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }, navigationIcon = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        }
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HealthTheme.colors.primary,
            titleContentColor = HealthTheme.colors.primary,
            navigationIconContentColor = HealthTheme.colors.iconColor,
            actionIconContentColor = HealthTheme.colors.iconColor,
        ),
        modifier = Modifier.height(56.dp)
    )
}

@Composable
fun StepStatsItem(dateString: String, steps: Long) {
    // Форматируем дату из yyyyMMdd в более читаемый вид
    val formattedDate = remember(dateString) {
        runCatching {
            val sdfInput = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val date = sdfInput.parse(dateString)
            val sdfOutput = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            sdfOutput.format(date ?: Date())
        }.getOrElse { dateString }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedDate,
            style = MaterialTheme.typography.bodyLarge.copy(color = HealthTheme.colors.text)
        )
        Text(
            text = "$steps шагов",
            style = MaterialTheme.typography.bodyLarge.copy(color = HealthTheme.colors.text)
        )
    }
}