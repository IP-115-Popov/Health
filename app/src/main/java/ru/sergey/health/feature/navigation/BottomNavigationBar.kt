package ru.sergey.health.feature.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.sergey.health.ui.theme.ui.HealthTheme

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
                    else HealthTheme.colors.iconColorOff
                )
            }, label = {
                Text(
                    text = navItem.title,
                    style = HealthTheme.typography.navigation, // Используем стиль из темы
                    color = if (currentRoute == navItem.route) HealthTheme.colors.iconColor
                    else HealthTheme.colors.iconColorOff
                )

            })
        }
    }
}

private data class BarItem(
    val title: String, val image: ImageVector, val route: String
)

private object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Задачи", image = Icons.Filled.Home, route = NavRoutes.TasksScreen.route
        ),
        BarItem(
            title = "Графы", image = Icons.Filled.Done, route = NavRoutes.GraphScreen.route
        ),
        BarItem(
            title = "Профиль",
            image = Icons.Filled.Person,
            route = NavRoutes.ProfileScreen.route
        ),
        BarItem(
            title = "Достижения",
            image = Icons.Filled.Star,
            route = NavRoutes.AchievementScreen.route
        ),
    )
}