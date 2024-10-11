package ru.sergey.health.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import ru.sergey.health.R
import ru.sergey.health.presentation.viewmodel.AddTasksViewModel


//@Preview(showSystemUi = true)
@Composable
fun AddTasksScreen
            //()
            (vm: AddTasksViewModel)
{
    val toastAdded = Toast.makeText(
        LocalContext.current,
        "Запись добавленна", Toast.LENGTH_SHORT
    )
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (tfTitle, tfDescription, tfTargetPoints, bthAdd) = createRefs()

        val titleText = remember{mutableStateOf("")}
        val descriptionText = remember{mutableStateOf("")}
        val targetPointsText = remember{mutableStateOf("")}

        createVerticalChain(tfTitle, tfDescription, tfTargetPoints, bthAdd,
            chainStyle = ChainStyle.Packed)

        StyledTextField( modifier = Modifier.constrainAs(tfTitle) {
            top.linkTo(parent.top, margin = 16.dp)
            centerHorizontallyTo(parent)
        },
            titleText,
            stringResource(R.string.title),
            KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done))

        StyledTextField( modifier = Modifier.constrainAs(tfDescription) {
            top.linkTo(tfTitle.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        },
            descriptionText,
            stringResource(R.string.description),
            KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done))

        StyledTextField( modifier = Modifier.constrainAs(tfTargetPoints) {
            top.linkTo(tfDescription.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        },
            targetPointsText,
            stringResource(R.string.TargetPoints),
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done))

        Button(onClick = {
            vm.addTask(titleText.value, descriptionText.value, targetPointsText.value)
            toastAdded.show()
        },
            Modifier
                .constrainAs(bthAdd) {
                    top.linkTo(tfTargetPoints.bottom, margin = 16.dp)
                    centerHorizontallyTo(tfTargetPoints)
                }
                .size(150.dp, height = 120.dp)
                .padding(top = 50.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xff004D40),       // цвет текста
                containerColor = Color(0xff9ed6df))  ,   // цвет фона
            border = BorderStroke(3.dp, Color.DarkGray)
            ){
            Text(stringResource(R.string.addTask), fontSize = 25.sp)
        }

    }

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