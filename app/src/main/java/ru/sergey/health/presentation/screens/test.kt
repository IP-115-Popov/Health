//package ru.sergey.health.presentation.screens
//
//@Composable
//fun EditableForm() {
//    // Состояния для полей текста
//    var name by remember { mutableStateOf("John Doe") }
//    var email by remember { mutableStateOf("john.doe@example.com") }
//
//    // Состояние, которое определяет, можно ли редактировать поля
//    var isEditable by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        // Поле для имени
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Name") },
//            enabled = isEditable,  // Поле будет редактируемым только если isEditable == true
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Поле для email
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            enabled = isEditable,  // Поле будет редактируемым только если isEditable == true
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Кнопка редактирования
//        Button(
//            onClick = { isEditable = !isEditable },  // Меняем состояние редактирования
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(if (isEditable) "Сохранить" else "Редактировать")
//        }
//    }
//}
