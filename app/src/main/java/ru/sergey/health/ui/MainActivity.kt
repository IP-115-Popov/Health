package ru.sergey.health.ui

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
import ru.sergey.health.feature.achievement.vm.AchievementViewModel
import ru.sergey.health.feature.graph.ui.screens.GraphScreen
import ru.sergey.health.feature.graph.viewmodel.GraphViewModel
import ru.sergey.health.feature.navigation.BottomNavigationBar
import ru.sergey.health.feature.navigation.NavRoutes
import ru.sergey.health.feature.navigation.NavigationGraph
import ru.sergey.health.feature.newtask.ui.screens.AddTasksScreen
import ru.sergey.health.feature.newtask.viewmodel.AddTasksViewModel
import ru.sergey.health.feature.profile.ui.screens.ProfileScreen
import ru.sergey.health.feature.profile.viewmodel.ProfileViewModel
import ru.sergey.health.feature.task.ui.screens.TasksScreen
import ru.sergey.health.feature.task.viewmodel.TasksViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tasksViewModel: TasksViewModel by viewModels()
    private val addTasksViewModel: AddTasksViewModel by viewModels()
    private val graphViewModel: GraphViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val achievementViewModel: AchievementViewModel by viewModels()

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
                    Main(
                        tasksViewModel = tasksViewModel,
                        addTasksViewModel = addTasksViewModel,
                        graphViewModel = graphViewModel,
                        profileViewModel = profileViewModel,
                        achievementViewModel = achievementViewModel
                    )
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
    tasksViewModel: TasksViewModel,
    addTasksViewModel: AddTasksViewModel,
    graphViewModel: GraphViewModel,
    profileViewModel: ProfileViewModel,
    achievementViewModel: AchievementViewModel,
) {
    val navController = rememberNavController()
    Column {
        NavigationGraph(
            navController = navController,
            tasksViewModel = tasksViewModel,
            addTasksViewModel = addTasksViewModel,
            graphViewModel = graphViewModel,
            profileViewModel = profileViewModel,
            achievementViewModel = achievementViewModel
        )

        BottomNavigationBar(
            navController = navController, modifier = Modifier.fillMaxHeight()
        )
    }
}


