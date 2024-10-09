package ru.sergey.health.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.sergey.health.presentation.viewmodel.TasksViewModel
import ru.sergey.health.presentation.screens.TasksScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}
@Composable
fun Main(vm : TasksViewModel) {
    val navController = rememberNavController()
    Column {
        NavHost(
            navController = navController,
            startDestination  = NavRoutes.TasksScreen.route,
            ) {
            composable(NavRoutes.TasksScreen.route) { TasksScreen(vm) }
        }
    }
}
sealed class NavRoutes(val route: String) {
    object TasksScreen : NavRoutes("TasksScreen")
}
