package ru.sergey.health.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import ru.sergey.health.feature.step.RunningService.Companion.CHANNEL_ID


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SetupStrictMode()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Running Notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}