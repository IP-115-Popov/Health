package ru.sergey.health.feature.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sergey.health.feature.achievement.ui.screen.AchievementScreen
import ru.sergey.health.feature.achievement.vm.AchievementViewModel
import ru.sergey.health.feature.graph.ui.screens.GraphScreen
import ru.sergey.health.feature.graph.viewmodel.GraphViewModel
import ru.sergey.health.feature.newtask.ui.screens.AddTasksScreen
import ru.sergey.health.feature.newtask.viewmodel.AddTasksViewModel
import ru.sergey.health.feature.profile.ui.screens.ProfileScreen
import ru.sergey.health.feature.profile.viewmodel.ProfileViewModel
import ru.sergey.health.feature.task.ui.screens.TasksScreen
import ru.sergey.health.feature.task.viewmodel.TasksViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    tasksViewModel: TasksViewModel,
    addTasksViewModel: AddTasksViewModel,
    graphViewModel: GraphViewModel,
    profileViewModel: ProfileViewModel,
    achievementViewModel: AchievementViewModel,
) {
    NavHost(
    navController = navController,
    startDestination = NavRoutes.TasksScreen.route,
    modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        composable(NavRoutes.TasksScreen.route) {
            TasksScreen(tasksViewModel, navController)
        }
        composable(NavRoutes.ProfileScreen.route) {
            ProfileScreen(profileViewModel, navController)
        }
        composable(NavRoutes.AddTasksScreen.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 0
            AddTasksScreen(addTasksViewModel, navController, taskId)
        }
        composable(NavRoutes.GraphScreen.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 0
            GraphScreen(taskId, graphViewModel, tasksViewModel, navController)
        }
        composable(NavRoutes.AchievementScreen.route) {
            AchievementScreen(navController = navController, achievementViewModel = achievementViewModel)
        }
    }
}