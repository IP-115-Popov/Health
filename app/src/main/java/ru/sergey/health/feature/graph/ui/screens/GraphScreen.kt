package ru.sergey.health.feature.graph.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
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
import ru.sergey.health.R
import ru.sergey.health.feature.graph.viewmodel.GraphViewModel
import ru.sergey.health.feature.task.viewmodel.TasksViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme
import java.text.SimpleDateFormat
import java.util.Locale

fun String.toMillis(): Long {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.parse(this)?.time ?: 0L
}

@Composable
fun GraphScreen(
    taskId: Int,
    graphViewModel: GraphViewModel,
    tasksViewModel: TasksViewModel,
    navController: NavHostController
) {
    val graphState = graphViewModel.state.collectAsState()
    val tasksState = tasksViewModel.tasksUiState.collectAsState()

    val initialPage = 0

    val pagerState = rememberPagerState(initialPage = initialPage) { tasksState.value.tasks.size }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (tasksState.value.tasks.size > 0) {
            val task = tasksState.value.tasks[pagerState.currentPage]
            graphViewModel.loadData(task.id) // Загрузка данных для текущей страницы
        }
    }

    Scaffold(
        topBar = { GraphTopBar(navController) },
        containerColor = HealthTheme.colors.background,
    ) { innerPadding ->


        HorizontalPager(
            state = pagerState, Modifier.fillMaxHeight(), contentPadding = innerPadding
        ) { page ->
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                val product = tasksState.value.tasks[page]
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        product.title,
                        style = HealthTheme.typography.h1.copy(
                            color = HealthTheme.colors.text, fontSize = 42.sp
                        ),
                        modifier = Modifier.align(Alignment.Center),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Box(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f)
                        .background(HealthTheme.colors.card)
                ) {
                    if (graphState.value.pointsList.size <= 1) {
                        Text(
                            text = stringResource(R.string.no_data),
                            style = HealthTheme.typography.h1
                        )
                    } else {
                        PointsGraph(graphState.value.pointsList.map { it.date.toMillis() to it.points })
                    }

                    Text(
                        text = "time",
                        style = HealthTheme.typography.h1,
                        color = HealthTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    )

                    Text(
                        text = "point",
                        style = HealthTheme.typography.h1,
                        color = HealthTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                    )

                    Text(
                        text = "0",
                        style = HealthTheme.typography.h1,
                        color = HealthTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )

                    if (page != 0) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Previous Page",
                            tint = HealthTheme.colors.iconColor.copy(alpha = 0.3f),
                            modifier = Modifier
                                .size(75.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                                .align(Alignment.CenterStart)
                        )
                    }

                    if (page != tasksState.value.tasks.lastIndex) {
                        Icon(imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Next Page",
                            tint = HealthTheme.colors.iconColor.copy(alpha = 0.3f),
                            modifier = Modifier
                                .size(75.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                                .align(Alignment.CenterEnd)
                        )
                    }
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
            color = Color.Black, start = Offset(30f, 0f), // Ось Y начинается с нижнего левого угла
            end = Offset(30f, canvasHeight), strokeWidth = 3f
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
                color = Color.Blue, radius = 7f, center = Offset(x, y)
            )
        }

        // Соединяем точки линией
        points.zipWithNext { current, next ->
            val x1 = (current.first - minDate) * xScale + 30f // Сдвиг X на 30f
            val y1 = canvasHeight - 30f - (current.second - minPoints) * yScale // Сдвиг Y на 30f

            val x2 = (next.first - minDate) * xScale + 30f // Сдвиг X на 30f
            val y2 = canvasHeight - 30f - (next.second - minPoints) * yScale // Сдвиг Y на 30f

            drawLine(
                color = Color.Red, start = Offset(x1, y1), end = Offset(x2, y2), strokeWidth = 3f
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