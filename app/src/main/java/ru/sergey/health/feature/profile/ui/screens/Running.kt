package ru.sergey.health.feature.profile.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ru.sergey.health.feature.profile.viewmodel.StepCounterViewModel
import ru.sergey.health.ui.theme.ui.HealthTheme

@Composable
fun Running(viewModel: StepCounterViewModel) {
    val context = LocalContext.current
    var hasSensor by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.startListening()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
        }
    }

    if (!hasSensor) {
        Text("Датчик шагов не доступен")
    } else {
        Column {
            Text(
                text = "Шаги: ${viewModel.steps.value}",
                style = HealthTheme.typography.h1
                    .copy(color = HealthTheme.colors.text),
            )

            Text(
                text = "Дистанция: ${"%.2f".format(viewModel.distanceKm.value)} км",
                style = HealthTheme.typography.h1
                    .copy(color = HealthTheme.colors.text),
            )
        }
    }

    // Проверка наличия датчика
    LaunchedEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        hasSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null
    }
}