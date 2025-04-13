//package ru.sergey.health.presentation
//
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.viewModels
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import dagger.hilt.android.AndroidEntryPoint
//import ru.sergey.health.presentation.screens.AddTasksScreen
//import ru.sergey.health.presentation.screens.GraphScreen
//import ru.sergey.health.presentation.screens.ProfileScreen
//import ru.sergey.health.presentation.screens.TasksScreen
//import ru.sergey.health.presentation.theme.ui.HealthTheme
//import ru.sergey.health.presentation.viewmodel.AddTasksViewModel
//import ru.sergey.health.presentation.viewmodel.GraphViewModel
//import ru.sergey.health.presentation.viewmodel.ProfileViewModel
//import ru.sergey.health.presentation.viewmodel.TasksViewModel
//import android.Manifest.permission.READ_MEDIA_IMAGES
//import android.Manifest.permission.READ_EXTERNAL_STORAGE
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.content.Intent
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.SideEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.PermissionStatus
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.shouldShowRationale
//import android.provider.Settings
//
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "your_channel_id"
//            val channelName = "Your Channel Name"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//
//            // Создаем канал уведомлений
//            val channel = NotificationChannel(channelId, channelName, importance).apply {
//                description = "Your channel description"
//            }
//
//            // Получаем NotificationManager и создаем канал
//            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            if (!notificationManager.areNotificationsEnabled()) {
//                // Запрашиваем разрешение на уведомления
//                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
//                    putExtra(Settings.EXTRA_APP_PACKAGE, applicationContext.packageName)
//                }
//                applicationContext.startActivity(intent)
//            }
//        }
//        setContent {
//            HealthTheme {
//                GetPermission()
//            }
//        }
//    }
//}
//@Composable
//fun GetPermission(){
//    val louncher =  rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()) { isPermissionGranded ->
//    }
//
//    SideEffect {
//        louncher.launch(READ_EXTERNAL_STORAGE)
//    }
//}
