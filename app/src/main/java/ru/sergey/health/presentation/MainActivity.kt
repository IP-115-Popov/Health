package ru.sergey.health.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sergey.health.presentation.viewmodel.TasksViewModel
import ru.sergey.health.presentation.screens.TasksScreen
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel
import ru.sergey.health.presentation.screens.AddTasksScreen
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tasksViewModel : TasksViewModel by viewModels()
    private val addTasksViewModel : AddTasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main(tasksViewModel, addTasksViewModel)
        }
    }
}
@Composable
fun Main(tasksViewModel : TasksViewModel, addTasksViewModel: AddTasksViewModel ) {
    val navController = rememberNavController()
    Column {
        NavHost(
            navController = navController,
            startDestination  = NavRoutes.TasksScreen.route,
            ) {
            composable(NavRoutes.TasksScreen.route) { TasksScreen(tasksViewModel) }
            composable(NavRoutes.AddTasksScreen.route) { AddTasksScreen(addTasksViewModel) }
        }
    }
}
sealed class NavRoutes(val route: String) {
    object TasksScreen : NavRoutes("TasksScreen")
    object AddTasksScreen : NavRoutes("AddTasksScreen")
}
