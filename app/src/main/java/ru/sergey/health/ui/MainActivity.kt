package ru.sergey.health.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sergey.health.feature.achievement.vm.AchievementViewModel
import ru.sergey.health.feature.graph.viewmodel.GraphViewModel
import ru.sergey.health.feature.navigation.BottomNavigationBar
import ru.sergey.health.feature.navigation.NavigationGraph
import ru.sergey.health.feature.newtask.viewmodel.AddTasksViewModel
import ru.sergey.health.feature.profile.viewmodel.ProfileViewModel
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
                GetMediaPermission {
                    p.value = it
                }
                if (p.value) {
                    Main(
                        tasksViewModel = tasksViewModel,
                        addTasksViewModel = addTasksViewModel,
                        graphViewModel = graphViewModel,
                        profileViewModel = profileViewModel,
                        achievementViewModel = achievementViewModel
                    )
                } else {
                    // Optionally, show a message to the user explaining why the permission is needed
                    Text("Permission required to access storage.")
                }
            }
        }
    }
}

@Composable
fun GetPermission(onPermissionResult: (Boolean) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    val permissionCheckResult = rememberUpdatedState(
        ContextCompat.checkSelfPermission(
            context,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    )

    SideEffect {
        if (permissionCheckResult.value) {
            onPermissionResult(true) // Permission already granted
        } else {
            launcher.launch(READ_EXTERNAL_STORAGE) // Request the permission
        }
    }
}

@Composable
fun GetMediaPermission(onPermissionResult: (Boolean) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        onPermissionResult(allGranted)
    }

    val permissionsToRequest = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionsToRequest.apply {
            add(READ_MEDIA_IMAGES)
            add(READ_MEDIA_VIDEO)
            add(READ_MEDIA_AUDIO)
        }
    } else {
        permissionsToRequest.add(READ_EXTERNAL_STORAGE)
    }

    val allPermissionsAlreadyGranted = rememberUpdatedState(permissionsToRequest.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    })

    SideEffect {
        if (allPermissionsAlreadyGranted.value) {
            onPermissionResult(true)
        } else {
            launcher.launch(permissionsToRequest.toTypedArray())
        }
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


