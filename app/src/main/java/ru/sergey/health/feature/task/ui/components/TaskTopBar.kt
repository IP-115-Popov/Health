package ru.sergey.health.feature.task.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.sergey.health.R
import ru.sergey.health.feature.navigation.NavRoutes
import ru.sergey.health.ui.theme.ui.HealthTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = stringResource(R.string.tasks),
                style = HealthTheme.typography.h1,
                modifier = Modifier.align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }, navigationIcon = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        }
    }, actions = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            IconButton(
                onClick = {
                    val navRoute: String =
                        NavRoutes.AddTasksScreen.route.replace("{taskId}", "0")
                    navController.navigate(navRoute)
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add_24),
                    contentDescription = "Add"
                )
            }
        }
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HealthTheme.colors.primary,
            titleContentColor = HealthTheme.colors.primary,
            navigationIconContentColor = HealthTheme.colors.iconColor,
            actionIconContentColor = HealthTheme.colors.iconColor,
        ), modifier = Modifier.height(56.dp)
    )
}