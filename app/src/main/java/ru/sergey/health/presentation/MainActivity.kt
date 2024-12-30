package ru.sergey.health.presentation

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.sergey.health.presentation.screens.ProfileScreen
import ru.sergey.health.presentation.screens.TasksScreen
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel
import ru.sergey.health.presentation.viewmodel.GraphViewModel
import ru.sergey.health.presentation.viewmodel.ProfileViewModel
import ru.sergey.health.presentation.viewmodel.TasksViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tasksViewModel: TasksViewModel by viewModels()
    private val addTasksViewModel: AddTasksViewModel by viewModels()
    private val graphViewModel: GraphViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Создаем канал уведомлений, если это необходимо
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "your_channel_id"
            val channelName = "Your Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Your channel description"
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Проверка разрешений для уведомлений для Android 13 и выше
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!notificationManager.areNotificationsEnabled()) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            }
        }
        setContent {
            HealthTheme {
                val p = remember { mutableStateOf(false) }
                GetPermission(p)
                if (p.value) {
                    Main(this, tasksViewModel, addTasksViewModel, graphViewModel, profileViewModel)
                }
            }
        }
    }
}

@Composable
fun GetPermission(p: MutableState<Boolean>) {
    val louncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isPermissionGranded ->
        p.value = isPermissionGranded
    }

    SideEffect {
        louncher.launch(READ_EXTERNAL_STORAGE)
    }
}

@Composable
fun Main(
    context: Context,
    tasksViewModel: TasksViewModel,
    addTasksViewModel: AddTasksViewModel,
    graphViewModel: GraphViewModel,
    profileViewModel: ProfileViewModel
) {
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
            composable(NavRoutes.ProfileScreen.route) {
                ProfileScreen(context = context, profileViewModel, navController)
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
            NavigationBarItem(selected = currentRoute == navItem.route, onClick = {
                navController.navigate(navItem.route)
            }, icon = {
                Icon(
                    imageVector = navItem.image,
                    contentDescription = navItem.title,
                    tint = if (currentRoute == navItem.route) HealthTheme.colors.iconColor
                    else HealthTheme.colors.placeholderText
                )
            }, label = {
                Text(
                    text = navItem.title,
                    style = HealthTheme.typography.navigation, // Используем стиль из темы
                    color = if (currentRoute == navItem.route) HealthTheme.colors.iconColor else HealthTheme.colors.placeholderText
                )

            })
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
        BarItem(
            title = "ProfileScreen",
            image = Icons.Filled.Person,
            route = NavRoutes.ProfileScreen.route
        ),
    )
}

sealed class NavRoutes(val route: String) {
    object TasksScreen : NavRoutes("TasksScreen")
    object AddTasksScreen : NavRoutes("AddTasksScreen/{taskId}")
    object GraphScreen : NavRoutes("GraphScreen/{taskId}")
    object ProfileScreen : NavRoutes("ProfileScreen")
}
