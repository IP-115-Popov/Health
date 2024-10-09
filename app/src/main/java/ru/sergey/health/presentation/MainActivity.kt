package ru.sergey.health.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sergey.health.presentation.viewmodel.TasksViewModel
import ru.sergey.health.presentation.screens.TasksScreen
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel
import ru.sergey.health.presentation.screens.AddTasksScreen
import ru.sergey.health.presentation.theme.ui.PurpleGrey80

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
            modifier = Modifier.fillMaxHeight(0.9f)
        ) {
            composable(NavRoutes.TasksScreen.route) { TasksScreen(tasksViewModel) }
            composable(NavRoutes.AddTasksScreen.route) { AddTasksScreen(addTasksViewModel) }
        }
        BottomNavigationBar(navController = navController, modifier = Modifier
            .fillMaxHeight())
    }
}
@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
        NavigationBar(modifier) {
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry.value?.destination?.route

            NavBarItems.BarItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.image,
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(text = navItem.title)
                    }
                )
            }
        }
}
data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)
object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "TaskScreen",
            image = Icons.Filled.Home,
            route = NavRoutes.TasksScreen.route
        ),
        BarItem(
            title = "AstronomicalScreen",
            image = Icons.Filled.AddCircle,
            route = NavRoutes.AddTasksScreen.route
        ),

        )
}
sealed class NavRoutes(val route: String) {
    object TasksScreen : NavRoutes("TasksScreen")
    object AddTasksScreen : NavRoutes("AddTasksScreen")
}
