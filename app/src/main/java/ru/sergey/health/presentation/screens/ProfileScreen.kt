package ru.sergey.health.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import ru.sergey.health.R
import ru.sergey.health.presentation.NavRoutes
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    val player = viewModel.state.collectAsState()
    val isEditable = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { ProfileTopBar(navController, expanded, isEditable) }
    ) { innerPadding->
        ConstraintLayout(modifier = Modifier
            .background(HealthTheme.colors.background)
            .fillMaxSize()
            .padding(innerPadding)) {
            val (avatar, name, level, exp, bthEdit, tasksCount) = createRefs()

            StyledEditableTextField(
                text =  player.value.player.name,
                placeholderText = "Name",
                enabled = isEditable.value,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                },
                onValueChange = {viewModel.setName(it)}
            )

            Column(modifier = Modifier
                .constrainAs(level) {
                    top.linkTo(name.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                }
                .padding(8.dp)
                .background(HealthTheme.colors.card, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(0.5f)
                .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "level",
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                )
                Text(
                    text = player.value.player.level.toString(),
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                )
            }

            Column (modifier = Modifier
                .constrainAs(exp) {
                    top.linkTo(level.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
                .padding(8.dp)
                .background(HealthTheme.colors.card, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(0.5f)
                .height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "exp",
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                )
                Text(
                    text = player.value.player.ex.toString(),
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                )
            }
            Column(modifier = Modifier
                .constrainAs(tasksCount) {
                    top.linkTo(exp.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                }
                .padding(8.dp)
                .background(HealthTheme.colors.card, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(0.5f)
                .height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Closed Tasks" ,
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                )
                Text(
                    text = player.value.player.closeTasksId.size.toString() + "/" + player.value.player.openTasksId.size.toString() ,
                    style = HealthTheme.typography.h1
                        .copy(color = HealthTheme.colors.text),
                )
            }

            if (isEditable.value) {
                Box(modifier = Modifier
                    .constrainAs(bthEdit) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        centerHorizontallyTo(parent)
                    }
                    .padding(8.dp)
                    .background(HealthTheme.colors.card, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(50.dp)

                ) {

                    Button(
                        onClick = {
                            isEditable.value = !(isEditable.value)
                            viewModel.savePlayer()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Сохранить")
                    }
                }
            }
        }
    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd,
    ) {
        Box(
            Modifier.offset(y = 50.dp)
        ) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded.value = false
                        isEditable.value = true
                    },
                    text = { Text("Редактировать") }
                )
            }
        }
    }
}


@Composable
fun StyledEditableTextField(
    text: String,
    placeholderText: String,
    enabled: Boolean,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
) {
    val isFocused = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        enabled = enabled,
        value = text,
        onValueChange = { newText ->
            if (newText != text) {
                onValueChange(newText)
            }
        },
        placeholder = {
            Text(
                placeholderText,
                style = HealthTheme.typography.body1.copy(color = HealthTheme.colors.placeholderText),
            )
        },
        singleLine = true,
        textStyle = HealthTheme.typography.body1.copy(color = HealthTheme.colors.text),
        trailingIcon = {
            if (enabled) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Проверено",
                    modifier = Modifier.clickable {
                        onValueChange("")
                    },
                    tint = if (isFocused.value) {
                        HealthTheme.colors.iconColor
                    } else {
                        HealthTheme.colors.primary
                    }
                )
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    navController: NavHostController,
    expanded: MutableState<Boolean>,
    isEditable: MutableState<Boolean>,
) {
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
    },
    actions = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            IconButton(
                onClick = {
                    expanded.value = true
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector =  Icons.Default.Menu,
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
    ),
        modifier = Modifier.height(56.dp)
    )
}