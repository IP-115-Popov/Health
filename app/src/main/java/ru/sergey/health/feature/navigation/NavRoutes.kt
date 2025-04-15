package ru.sergey.health.feature.navigation

sealed class NavRoutes(val route: String) {
    object TasksScreen : NavRoutes("TasksScreen")
    object AddTasksScreen : NavRoutes("AddTasksScreen/{taskId}")
    object GraphScreen : NavRoutes("GraphScreen/{taskId}")
    object ProfileScreen : NavRoutes("ProfileScreen")
}