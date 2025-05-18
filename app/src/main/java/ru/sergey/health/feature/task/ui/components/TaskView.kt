package ru.sergey.health.feature.task.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.sergey.domain.task.models.Task
import ru.sergey.health.feature.task.viewmodel.TasksViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme

@Composable
fun TaskView(task: Task, vm: TasksViewModel, edit: () -> Unit) {
    TaskView(
        task = task,
        deleteTaskById =  vm::deleteTaskById,
        addPointsToTask = vm::addPointsToTask,
        edit = edit,
    )
}

@Composable
fun TaskView(
    task: Task,
    deleteTaskById: (Int)->Unit,
    addPointsToTask: (Int)->Unit,
    edit: () -> Unit
) {
    val textModifier = Modifier.padding(5.dp)

    var expanded by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (task.points >= task.targetPoints) HealthTheme.colors.green else HealthTheme.colors.card,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            .height(142.dp)
            .fillMaxWidth()
    ) {
        val (tvTitle, tvDescription, tvPoint, bthEdit, dthAddPoint, dropdownMenu) = createRefs()

        Box(modifier = textModifier
            .background(color = HealthTheme.colors.yellow, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
            .then(Modifier.constrainAs(tvTitle) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 20.dp)
            })) {
            Text(
                text = task.title,
                color = HealthTheme.colors.background,
                style = HealthTheme.typography.h1.copy(color = HealthTheme.colors.text),
            )
        }

        Text(
            text = task.description,
            style = HealthTheme.typography.body1.copy(color = HealthTheme.colors.text),
            modifier = textModifier
                .then(Modifier.constrainAs(tvDescription) {
                    top.linkTo(bthEdit.bottom)
                    start.linkTo(parent.start, margin = 20.dp)
                })
                .padding(end = 16.dp),
            color = HealthTheme.colors.text,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Box(modifier = Modifier
            .background(color = HealthTheme.colors.yellow, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
            .then(Modifier.constrainAs(tvPoint) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 20.dp)
            })) {
            Text(
                text = task.points.toString() + "/" + task.targetPoints + "   " + task.measureUnit,
                color = HealthTheme.colors.background,
                style = HealthTheme.typography.body1.copy(color = HealthTheme.colors.text),
                modifier = textModifier
            )
        }

        IconButton(onClick = {
            expanded = true
        },
            modifier = Modifier
                .size(40.dp)
                .constrainAs(bthEdit) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(imageVector = Icons.Filled.List, contentDescription = "Меню задач")
        }
        IconButton(onClick = {
            addPointsToTask(task.id)
        },
            modifier = Modifier
                .size(40.dp)
                .padding()
                .background(HealthTheme.colors.yellow, shape = CircleShape)
                .constrainAs(dthAddPoint) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
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
                        deleteTaskById(task.id)
                    },
                    text = { Text("Удалить") }
                )
            }
        }
    }
}
@Preview
@Composable
private fun Preview() {
    HealthTheme {
        TaskView(
            task = Task(
                id = 0,
                title = "run",
                description = "rundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaarundoigjaaaaaaaaaaaaaaaaaa",
                points = 1000,
                targetPoints = 1000,
                measureUnit = "km"
            ),
            deleteTaskById = { },
            addPointsToTask = { },
            edit = {},
        )
    }
}