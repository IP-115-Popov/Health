package ru.sergey.health.feature.task.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.sergey.health.feature.navigation.NavRoutes
import ru.sergey.health.feature.task.ui.components.TaskTopBar
import ru.sergey.health.feature.task.ui.components.TaskView
import ru.sergey.health.feature.task.viewmodel.TasksViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme

@Composable
fun TasksScreen(vm: TasksViewModel, navController: NavHostController) {
    val tasks = vm.tasksUiState.collectAsState()

    Scaffold(
        topBar = { TaskTopBar(navController) },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(HealthTheme.colors.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(tasks.value.tasks) { item ->
                TaskView(item, vm,
                    edit = {
                        val navRoute: String =
                            NavRoutes.AddTasksScreen.route.replace("{taskId}", "${item.id}")
                        navController.navigate(navRoute)
                    }
                )
            }
        }
    }
}