package ru.sergey.health.feature.step

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.sergey.health.R

class RunningService : Service() {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                // Обработка данных
                val steps = event.values[0].toLong()
                sendStepCount(steps)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        start()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            // Шагомер недоступен
            showStepSensorUnavailableNotification()
            // Можно остановить сервис, если нет смысла работать дальше
            return
        }

        sensorManager.registerListener(
            sensorListener,
            stepSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorListener)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.heart)
            .setContentTitle("Шагомер запущен1")
            .setContentText("Отслеживание шагов в фоновом режиме")
            .build()
        startForeground(1, notification)
    }

    private fun sendStepCount(steps: Long) {
        val intent = Intent("com.example.STEP_COUNT_UPDATE")
        intent.putExtra("steps", steps)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun showStepSensorUnavailableNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.heart)
            .setContentTitle("Шагомер недоступен")
            .setContentText("Ваше устройство не поддерживает подсчет шагов")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)

        startForeground(1, notification)
    }

    companion object {
        const val CHANNEL_ID = "running_chanel"
        const val STEP_MESSAGE_ID = "com.example.STEP_COUNT_UPDATE"
    }
}