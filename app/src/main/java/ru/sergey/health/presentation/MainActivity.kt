package ru.sergey.health.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sergey.health.presentation.screens.AddTasksScreen
import ru.sergey.health.presentation.screens.GraphScreen
import ru.sergey.health.presentation.screens.TasksScreen
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel
import ru.sergey.health.presentation.viewmodel.GraphViewModel
import ru.sergey.health.presentation.viewmodel.TasksViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tasksViewModel: TasksViewModel by viewModels()
    private val addTasksViewModel: AddTasksViewModel by viewModels()
    private val graphViewModel: GraphViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthTheme {
                Main(tasksViewModel, addTasksViewModel, graphViewModel)
            }
        }
    }
}

@Composable
fun Main(tasksViewModel: TasksViewModel, addTasksViewModel: AddTasksViewModel, graphViewModel: GraphViewModel) {
    val navController = rememberNavController()
    Column {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.TasksScreen.route,
            modifier = Modifier.fillMaxHeight(0.9f)
        ) {
            composable(NavRoutes.TasksScreen.route) {
                TasksScreen(tasksViewModel, navController)
            }
            composable(NavRoutes.AddTasksScreen.route) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 0
                AddTasksScreen(addTasksViewModel, navController, taskId)
            }
            composable(NavRoutes.GraphScreen.route) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 0
                GraphScreen(taskId, graphViewModel, tasksViewModel, navController)
            }

        }
        BottomNavigationBar(
            navController = navController, modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier,
        containerColor = HealthTheme.colors.primary,
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry.value?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title,
                        tint =
                        if (currentRoute == navItem.route) HealthTheme.colors.iconColor
                        else HealthTheme.colors.placeholderText
                    )
                }, label = {
                    Text(
                        text = navItem.title,
                        style = HealthTheme.typography.navigation, // Используем стиль из темы
                        color = if (currentRoute == navItem.route) HealthTheme.colors.iconColor else HealthTheme.colors.placeholderText
                    )

                }
            )
        }
    }
}

data class BarItem(
    val title: String, val image: ImageVector, val route: String
)

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "TaskScreen", image = Icons.Filled.Home, route = NavRoutes.TasksScreen.route
        ),
        BarItem(
            title = "GraphScreen", image = Icons.Filled.Star, route = NavRoutes.GraphScreen.route
        ),
    )
}

sealed class NavRoutes(val route: String) {
    object TasksScreen : NavRoutes("TasksScreen")
    object AddTasksScreen : NavRoutes("AddTasksScreen/{taskId}")
    object GraphScreen : NavRoutes("GraphScreen/{taskId}")
}
