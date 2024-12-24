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
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import ru.sergey.domain.models.Task
import ru.sergey.health.R
import ru.sergey.health.presentation.NavRoutes
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.theme.ui.Pink80
import ru.sergey.health.presentation.viewmodel.TasksViewModel

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
                TaskView(item, vm,
                    edit = {
                        val navRoute: String =
                            NavRoutes.AddTasksScreen.route.replace("{taskId}", "${item.id}")
                        navController.navigate(navRoute)
                    }
                )
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
                onClick = {
                    val navRoute: String =
                        NavRoutes.AddTasksScreen.route.replace("{taskId}", "0")
                    navController.navigate(navRoute)
                          },
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

@Composable
fun TaskView(task: Task, vm : TasksViewModel, edit: ()->Unit)
{
    val textModifier = Modifier.padding(5.dp)

    var expanded by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .padding(4.dp)
            .background(
                HealthTheme.colors.cardColor,
                RoundedCornerShape(10.dp)
            )
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            .height(142.dp)
            .fillMaxWidth()
    ) {
        val (tvTitle, tvDescription, tvPoint, bthEdit, dthAddPoint, dropdownMenu) = createRefs()

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
            expanded = true
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
            Icon(imageVector = Icons.Filled.List, contentDescription = "Меню задач")
        }
        IconButton(onClick = {
            vm.addPointsToTask(task.id)
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

        Box(modifier = Modifier.constrainAs(dropdownMenu) {
            top.linkTo(bthEdit.bottom)
            end.linkTo(parent.end)
        }) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        edit()
                    },
                    text = { Text("Редактировать") }
                )
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        vm.deleteTaskById(task.id)
                    },
                    text = { Text("Удалить") }
                )
            }
        }
    }
}