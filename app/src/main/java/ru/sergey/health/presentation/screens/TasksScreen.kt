package ru.sergey.health.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import ru.sergey.domain.models.Task
import ru.sergey.health.R
import ru.sergey.health.presentation.NavRoutes
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.theme.ui.Pink80
import ru.sergey.health.presentation.viewmodel.TasksViewModel
import kotlin.random.Random

@Composable
fun TasksScreen(vm : TasksViewModel, navController: NavHostController) {
val tasks = vm.tasksUiState.collectAsState()

    Scaffold(
        topBar = { taskTopBar(navController)},
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(HealthTheme.colors.primaryBackground)
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(tasks.value.tasks) { item ->
                TaskView(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun taskTopBar(navController: NavHostController) {
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
    },actions = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            IconButton(
                onClick = {navController.navigate(NavRoutes.AddTasksScreen.route)},
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector =  ImageVector.vectorResource(R.drawable.ic_add_24),
                    contentDescription = "Add"
                )
            }
        }
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = HealthTheme.colors.topBarContainerColor,
        titleContentColor = HealthTheme.colors.titleContentColor,
        navigationIconContentColor = HealthTheme.colors.iconColor,
        actionIconContentColor = HealthTheme.colors.iconColor,
    ), modifier = Modifier.height(56.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun TaskView(task: Task = Task(1,"Бег","Бегать каждый день по 10Бегать каждый день по 10 кмБегать каждый день по 10 км кмБегать каждый день по 10 кмБегать каждый день по 10 кмБегать каждый день по 10 кмБегать каждый день по 10 км", 0, 100, "Дней"))//, vm : TasksViewModel)
{
    val textModifier = Modifier.padding(5.dp)

    ConstraintLayout(
        modifier = Modifier
            .padding(4.dp)
            .background(
                HealthTheme.colors.cardColor,
                RoundedCornerShape(10.dp)
            )
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            .height(Random.nextInt(142, 200).dp)
            .fillMaxWidth()
    ) {
        val (tvTitle, tvDescription, tvPoint, bthEdit, dthAddPoint) = createRefs()

        Text(text = task.title, textModifier.then(Modifier.constrainAs(tvTitle){
            top.linkTo(parent.top)
            start.linkTo(parent.start, margin = 20.dp)
        }))
        Text(text = task.description,
            textModifier.then(Modifier.constrainAs(tvDescription){
            top.linkTo(bthEdit.bottom)
            start.linkTo(parent.start, margin = 20.dp)
            end.linkTo(bthEdit.start, margin = 8.dp)
            }),
            maxLines = 3, // Установите максимальное количество строк
            overflow = TextOverflow.Ellipsis // Если текст превышает, будет добавлено
        )
        Text(text = task.points.toString() + "/" + task.targetPoints + "   " + task.measureUnit, textModifier.then(Modifier.constrainAs(tvPoint){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, margin = 20.dp)
        }))

        IconButton(onClick = {
            //vm.addPointsToTask(task.id)
            //vm.updateTasks()
        },
            modifier = Modifier
                .size(40.dp)
                .background(Pink80, shape = CircleShape)
                .padding()
                .constrainAs(bthEdit) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ){
            Image(
                imageVector = Icons.Filled.Create,
                contentDescription = "Значок редактирования задачи",
            )
        }
        IconButton(onClick = {
            //vm.addPointsToTask(task.id)
            //vm.updateTasks()
        },
            modifier = Modifier
                .size(40.dp)
                .background(Pink80, shape = CircleShape)
                .padding()
                .constrainAs(dthAddPoint) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ){
            Image(
                imageVector = Icons.Filled.Add,
                contentDescription = "Значок редактирования задачи",
            )
        }
    }
}