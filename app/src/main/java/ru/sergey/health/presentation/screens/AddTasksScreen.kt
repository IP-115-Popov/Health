package ru.sergey.health.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import ru.sergey.health.R
import ru.sergey.health.presentation.theme.ui.HealthTheme
import ru.sergey.health.presentation.theme.ui.Purple40
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel

@Composable
fun AddTasksScreen(vm: AddTasksViewModel, navController: NavHostController) {
    Scaffold(
        topBar = { taskAddTopBar(navController)},
    ) { innerPadding ->
        val toastAdded = Toast.makeText(
            LocalContext.current,
            "Запись добавлена", Toast.LENGTH_SHORT
        )
        val toastNotAdded = Toast.makeText(
            LocalContext.current,
            "Заполните поля", Toast.LENGTH_SHORT
        )
        ConstraintLayout(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            val (tfTitle, tfDescription, tfTargetPoints, tfMeasureUnitText, bthAdd) = createRefs()

            val titleText = remember { mutableStateOf("") }
            val descriptionText = remember { mutableStateOf("") }
            val targetPointsText = remember { mutableStateOf("") }
            val measureUnitText = remember { mutableStateOf("") }


            StyledTextField(
                modifier = Modifier.constrainAs(tfTitle) {
                    top.linkTo(parent.top, margin = 16.dp)
                    centerHorizontallyTo(parent)
                },
                titleText,
                stringResource(R.string.title),
                KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done)
            )

            StyledTextField(
                modifier = Modifier.constrainAs(tfDescription) {
                    top.linkTo(tfTitle.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                },
                descriptionText,
                stringResource(R.string.description),
                KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done)
            )

            StyledTextField(
                modifier = Modifier.constrainAs(tfTargetPoints) {
                    top.linkTo(tfDescription.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                },
                targetPointsText,
                stringResource(R.string.TargetPoints),
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            )


            StyledTextField(
                modifier = Modifier.constrainAs(tfMeasureUnitText) {
                    top.linkTo(tfTargetPoints.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                },
                measureUnitText,
                stringResource(R.string.measure_unit),
                KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done)
            )

            Button(onClick = {
                if (titleText.value != "" && descriptionText.value != "" && targetPointsText.value != "") {
                    vm.addTask(
                        titleText.value,
                        descriptionText.value,
                        targetPointsText.value,
                        measureUnitText.value
                    )
                    toastAdded.show()
                } else {

                }
            },
                Modifier
                    .constrainAs(bthAdd) {
                        top.linkTo(tfMeasureUnitText.bottom, margin = 16.dp)
                        centerHorizontallyTo(tfTargetPoints)
                    }
                    .size(150.dp, height = 120.dp)
                    .padding(top = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xff004D40),       // цвет текста
                    containerColor = Color(0xff9ed6df)
                ),   // цвет фона
                border = BorderStroke(3.dp, Color.DarkGray)
            ) {
                Text(stringResource(R.string.addTask), fontSize = 25.sp)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun taskAddTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = "Задачи",
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
                onClick = {navController.navigateUp()},
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                    contentDescription = "Back"
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
fun  StyledTextField(
    modifier: Modifier,
    text: MutableState<String>,
    placeholderText: String, keyboardOptions :  KeyboardOptions
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        placeholder = { Text(placeholderText) },
        value = text.value,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        textStyle = TextStyle(fontSize=25.sp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xffeeeeee),
            unfocusedTextColor = Color(0xff888888),
            focusedContainerColor = Color.White,
            focusedTextColor = Color(0xff222222),
        ),
        leadingIcon = { Icon(Icons.Filled.Check, contentDescription = "Проверено") },
        trailingIcon = { Icon(Icons.Filled.Info, contentDescription = "Дополнительная информация") },
        onValueChange = {newText -> text.value = newText},
        modifier = modifier.then(Modifier.padding(10.dp)),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()})
       )
}