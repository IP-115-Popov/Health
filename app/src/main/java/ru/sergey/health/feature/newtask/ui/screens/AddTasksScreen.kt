package ru.sergey.health.feature.newtask.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import ru.sergey.health.R
import ru.sergey.health.feature.newtask.viewmodel.AddTasksViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme

@Composable
fun AddTasksScreen(vm: AddTasksViewModel, navController: NavHostController, id: Int = 0) {
    val state by vm.tasksUiState.collectAsState()

    // Toasts
    val toastAdded = Toast.makeText(
        LocalContext.current, "Запись добавлена", Toast.LENGTH_SHORT
    )
    val toastNotAdded = Toast.makeText(
        LocalContext.current, "Заполните поля", Toast.LENGTH_SHORT
    )

    LaunchedEffect(id) {
        vm.getTask(id)
    }

    Scaffold(
        topBar = { TaskAddTopBar(navController) },
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .background(HealthTheme.colors.background)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val (tfTitle, tfDescription, tfTargetPoints, tfMeasureUnitText, bthAdd, points) = createRefs()

            // Используем состояние ViewModel для отображения данных
            StyledTextField(modifier = Modifier.constrainAs(tfTitle) {
                top.linkTo(parent.top, margin = 16.dp)
                centerHorizontallyTo(parent)
            },
                text = state.titleText,
                placeholderText = stringResource(R.string.title),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
                ),
                onValueChange = { vm.updateTitle(it) })

            StyledTextField(modifier = Modifier.constrainAs(tfDescription) {
                top.linkTo(tfTitle.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            },
                text = state.descriptionText,
                placeholderText = stringResource(R.string.description),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
                ),
                onValueChange = { vm.updateDescription(it) })


            if (id != 0) { //Редактирование
                Row(modifier = Modifier.constrainAs(tfTargetPoints) {
                    top.linkTo(tfDescription.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                }) {
                    StyledTextField(modifier = Modifier.fillMaxWidth(0.5f),
                        text = state.points.toString(),
                        placeholderText = stringResource(R.string.points),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ),
                        onValueChange = { vm.updatePoints(it) })
                    StyledTextField(modifier = Modifier,
                        text = state.targetPointsText.toString(),
                        placeholderText = stringResource(R.string.TargetPoints),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ),
                        onValueChange = { vm.updateTargetPoints(it) })
                }
            } else {
                StyledTextField(modifier = Modifier.constrainAs(tfTargetPoints) {
                    top.linkTo(tfDescription.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                },
                    text = state.targetPointsText.toString(),
                    placeholderText = stringResource(R.string.TargetPoints),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    onValueChange = { vm.updateTargetPoints(it) })
            }



            StyledTextField(modifier = Modifier.constrainAs(tfMeasureUnitText) {
                top.linkTo(tfTargetPoints.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            },
                text = state.measureUnitText,
                placeholderText = stringResource(R.string.measure_unit),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
                ),
                onValueChange = { vm.updateMeasureUnit(it) })



            Button(
                onClick = {
                    if (state.titleText.isNotBlank() && state.descriptionText.isNotBlank() && state.targetPointsText > 0) {
                        if (id == 0) vm.addTask()
                        else vm.updateTask()
                        toastAdded.show()
                    } else {
                        toastNotAdded.show()
                    }
                },
                modifier = Modifier
                    .constrainAs(bthAdd) {
                        top.linkTo(tfMeasureUnitText.bottom, margin = 16.dp)
                        centerHorizontallyTo(tfTargetPoints)
                    }
                    .size(150.dp, height = 120.dp)
                    .padding(top = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = HealthTheme.colors.background,  // Цвет текста
                    containerColor = HealthTheme.colors.primary  // Акцентный цвет
                ),
                border = BorderStroke(2.dp, HealthTheme.colors.primary), // Цвет границы
                shape = RoundedCornerShape(12.dp), // Скругленные углы
            ) {
                Text(
                    text = if (id == 0) {
                        stringResource(R.string.addTask)
                    } else {
                        stringResource(R.string.Save)
                    },
                    style = HealthTheme.typography.button,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = stringResource(R.string.add_task),
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

@Composable
fun StyledTextField(
    modifier: Modifier,
    text: String,
    placeholderText: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit
) {
    val isFocused = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            if (newText != text) {
                onValueChange(newText)
            }
        },
        placeholder = {
            Text(
                placeholderText,
                style = HealthTheme.typography.body1.copy(color = HealthTheme.colors.placeholderText),// TextStyle(color = HealthTheme.colors.placeholderText, fontSize = 18.sp)
            )
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        textStyle = HealthTheme.typography.body1.copy(color = HealthTheme.colors.text),//TextStyle(fontSize = 18.sp, color = HealthTheme.colors.text),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Проверено",
                tint = if (isFocused.value) {
                    HealthTheme.colors.iconColor
                } else {
                    HealthTheme.colors.primary
                }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Дополнительная информация",
                tint = if (isFocused.value) {
                    HealthTheme.colors.iconColor
                } else {
                    HealthTheme.colors.primary
                }
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xfff3f3f3),
            focusedContainerColor = HealthTheme.colors.primary,  // Акцент на фоне при фокусе
            unfocusedTextColor = Color.Gray,
            focusedTextColor = Color.Black,
            cursorColor = HealthTheme.colors.primary,  // Цвет курсора
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)) // Скругленные углы
            .background(Color.White)
            .shadow(2.dp, RoundedCornerShape(12.dp)) // Тень для глубины
            .border(2.dp, Color(0xff004D40), RoundedCornerShape(12.dp)) // Граница поля
            .focusRequester(FocusRequester.Default) // Добавление запроса фокуса
            .onFocusChanged { focusState ->
                // Следим за фокусом
                isFocused.value = focusState.isFocused
            },
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}
