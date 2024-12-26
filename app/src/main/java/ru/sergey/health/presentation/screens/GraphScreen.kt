package ru.sergey.health.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.sergey.domain.models.Task
import ru.sergey.health.R
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.viewmodel.GraphViewModel
import ru.sergey.health.presentation.viewmodel.TasksViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random
fun String.toMillis(): Long {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.parse(this)?.time ?: 0L
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GraphScreen(taskId: Int, graphViewModel: GraphViewModel, tasksViewModel: TasksViewModel, navController: NavHostController) {
    val graphState = graphViewModel.state.collectAsState()
    val tasksState = tasksViewModel.tasksUiState.collectAsState()

    val initialPage = 0//tasksState.value.tasks.indexOfFirst { task: Task -> task.id == taskId }

    val pagerState = rememberPagerState(initialPage = initialPage){ tasksState.value.tasks.size }

    val coroutineScope = rememberCoroutineScope()

// LaunchedEffect для загрузки данных при изменении текущей страницы
    LaunchedEffect(pagerState.currentPage) {
        val task = tasksState.value.tasks[pagerState.currentPage]
        graphViewModel.loadData(task.id) // Загрузка данных для текущей страницы
    }

    Scaffold(
        topBar = { GraphTopBar(navController) },
    ) { innerPadding ->


        HorizontalPager(state = pagerState, Modifier.fillMaxHeight(), contentPadding = innerPadding) { page ->
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                val product = tasksState.value.tasks[page]
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                ) {
                    Text(
                        product.title,
                        style = HealthTheme.typography.h1.copy(
                            color = HealthTheme.colors.text,
                            fontSize = 42.sp
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                        .background(HealthTheme.colors.card)
                ) {
                    if (graphState.value.pointsList.size <= 1){
                        Text(text = stringResource(R.string.no_data), style = HealthTheme.typography.h1)
                    } else {
                        PointsGraph(graphState.value.pointsList.map { it.date.toMillis() to it.points })
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth(0.9f)){
                    Text(
                        text = product.description,
                        style = HealthTheme.typography.h1.copy(color = HealthTheme.colors.text, fontSize = 28.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Next Page",
                        modifier = Modifier
                            .size(75.dp)
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Next Page",
                        modifier = Modifier
                            .size(75.dp)
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun PointsGraph(points: List<Pair<Long, Int>>) {
    if (points.isEmpty()) return

    // Определяем минимальные и максимальные значения по датам и баллам
    val minDate = points.minOf { it.first }
    val maxDate = points.maxOf { it.first }
    val maxPoints = points.maxOf { it.second }
    val minPoints = points.minOf { it.second }

    // Рисуем график
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Ось X (время)
        drawLine(
            color = Color.Black,
            start = Offset(30f, canvasHeight - 30f), // Ось X начинается с нижнего левого угла
            end = Offset(canvasWidth, canvasHeight - 30f),
            strokeWidth = 3f
        )

        // Ось Y (баллы)
        drawLine(
            color = Color.Black,
            start = Offset(30f, 0f), // Ось Y начинается с нижнего левого угла
            end = Offset(30f, canvasHeight),
            strokeWidth = 3f
        )

        // Масштабирование по оси X (время)
        val xScale = (canvasWidth - 60f) / (maxDate - minDate)

        // Масштабирование по оси Y (баллы)
        val yScale = (canvasHeight - 60f) / (maxPoints - minPoints)

        // Нарисуем точки
        points.forEachIndexed { index, point ->
            // Переводим дату и баллы в координаты на графике
            val x = (point.first - minDate) * xScale + 30f // Сдвиг X на 30f
            val y = canvasHeight - 30f - (point.second - minPoints) * yScale // Сдвиг Y на 30f

            drawCircle(
                color = Color.Blue,
                radius = 7f,
                center = Offset(x, y)
            )
        }

        // Соединяем точки линией
        points.zipWithNext { current, next ->
            val x1 = (current.first - minDate) * xScale + 30f // Сдвиг X на 30f
            val y1 = canvasHeight - 30f - (current.second - minPoints) * yScale // Сдвиг Y на 30f

            val x2 = (next.first - minDate) * xScale + 30f // Сдвиг X на 30f
            val y2 = canvasHeight - 30f - (next.second - minPoints) * yScale // Сдвиг Y на 30f

            drawLine(
                color = Color.Red,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 3f
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = stringResource(R.string.graph),
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