package ru.sergey.health.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import ru.sergey.health.R
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    val player = viewModel.state.collectAsState()

    Scaffold(
        topBar = { ProfileTopBar(navController) }
    ) { innerPadding->
        ConstraintLayout(modifier = Modifier.background(HealthTheme.colors.background).fillMaxSize().padding(innerPadding)) {
            val (name,avatar, level, exp,) = createRefs()

            Box(modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }
                .padding(8.dp)
                .background(HealthTheme.colors.card, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(50.dp)
            ) {
                Text(
                    text = player.value.player.name,
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(modifier = Modifier.constrainAs(level) {
                top.linkTo(name.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            }
                .padding(8.dp)
                .background(HealthTheme.colors.card, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(50.dp)
            ) {
                Button(onClick = {viewModel.savePlayer()}) { Text("Save")}
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = stringResource(R.string.profile),
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
                onClick = { navController.navigateUp() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = HealthTheme.colors.primary,
        titleContentColor = HealthTheme.colors.primary,
        navigationIconContentColor = HealthTheme.colors.iconColor,
        actionIconContentColor = HealthTheme.colors.iconColor,
    ), modifier = Modifier.height(56.dp)
    )
}