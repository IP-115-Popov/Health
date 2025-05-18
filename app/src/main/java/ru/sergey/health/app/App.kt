package ru.sergey.health.app

import android.app.Application
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import dagger.hilt.android.HiltAndroidApp
import ru.sergey.health.BuildConfig

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) { // Включаем только в DEBUG сборках
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectDiskReads()          // Обнаруживать чтение с диска в основном потоке
                    .detectDiskWrites()         // Обнаруживать запись на диск в основном потоке
                    .detectNetwork()            // Обнаруживать сетевые операции в основном потоке
                    .detectCustomSlowCalls()    // Обнаруживать медленные пользовательские вызовы
                    .penaltyLog()               // Записывать нарушения в лог
                    .build()
            )

            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    // Обнаружение утечек памяти
                    .detectActivityLeaks()              // Утечки Activity
                    .detectLeakedClosableObjects()      // Не закрытые Closeable объекты
                    .detectLeakedRegistrationObjects() // Не отменённые регистрации (например, BroadcastReceiver)
                    .detectLeakedSqlLiteObjects()       // Не закрытые SQLite курсоры/объекты

                    // Обнаружение проблем безопасности
                    .detectFileUriExposure()            // Использование file:// URI без FileProvider
                    .detectContentUriWithoutPermission()// Доступ к ContentProvider без прав

                    // Обнаружение других проблем
                    .apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            detectUnsafeIntentLaunch()  // Небезопасный запуск Intent
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            detectCredentialProtectedWhileLocked()  // Доступ к зашифрованным данным при заблокированном устройстве
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            detectIncorrectContextUse() // Неправильное использование Context
                        }
                    }
                    .detectCleartextNetwork()           // Отправка данных по HTTP (без SSL)
                    .penaltyLog()                       // Записывать нарушения в лог
                    .build()
            )
        }
    }
}