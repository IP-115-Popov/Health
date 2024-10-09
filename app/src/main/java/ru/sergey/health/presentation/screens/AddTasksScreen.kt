package ru.sergey.health.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel

//@Preview(showSystemUi = true)
@Composable
fun AddTasksScreen
            //()
            (vm: AddTasksViewModel)
{
    ConstraintLayout {
        val rect = createRef()
        Box(
            Modifier
                .size(200.dp)
                .background(Color.DarkGray)
                .constrainAs(rect) {
                    top.linkTo(parent.top, margin = 16.dp)
                })
    }

}