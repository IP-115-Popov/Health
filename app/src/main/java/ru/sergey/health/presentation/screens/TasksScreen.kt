package ru.sergey.health.presentation.screens

import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.sergey.domain.models.Task
import ru.sergey.health.presentation.viewmodel.TasksViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sergey.health.R
import ru.sergey.health.presentation.theme.ui.Purple80
import kotlin.random.Random

@Composable
fun TasksScreen(vm : TasksViewModel) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),  // 3 столбца
        modifier = Modifier
            .fillMaxSize()
            .background(Purple80),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(vm.tasks) {item ->
            TaskView(item)
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun TaskView(task: Task = Task(1,"Бег","Бегать каждый день по 10 км", 0, 100, "Дней"))
{
    Surface(modifier = Modifier
        .shadow(5.dp)
        .clip(RoundedCornerShape(10.dp))) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .background(
                    Color(
                        Random.nextInt(255),
                        Random.nextInt(255),
                        Random.nextInt(255),
                        255
                    ), RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
                .height(Random.nextInt(120, 200).dp)
        ) {
            val textModifier = Modifier.padding(5.dp)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = task.title, textModifier)
                Button(onClick = {},
                ){
                    Image(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Значок увеличения очков",
                    )
                }
            }
            Text(text = task.description, textModifier)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = task.points.toString()+" / "+task.targetPoints.toString(), textModifier)
                Text(text = task.measureUnit)
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {},
                ){
                    Image(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Значок увеличения очков",
                    )
                }
            }

        }
    }
}