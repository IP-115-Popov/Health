package ru.sergey.health.feature.profile.viewmodel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class StepCounterViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ViewModel() {
    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null

    var steps = mutableStateOf(0L)
        private set
    var distanceKm = mutableStateOf(0.0)
        private set

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                steps.value = event.values[0].toLong()
                updateDistance()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    init {
        setupSensor()
    }

    private fun setupSensor() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Log.e("Steps", "No step sensor available!")
        }
    }

    fun startListening() {
        sensorManager?.registerListener(
            sensorListener,
            stepSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun stopListening() {
        sensorManager?.unregisterListener(sensorListener)
    }

    private fun updateDistance() {
        val stepLengthMeters = 0.75 // Средняя длина шага (можно настроить)
        distanceKm.value = steps.value * stepLengthMeters / 1000
    }
}