package ru.sergey.data.gamecontroller

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.sergey.domain.gamecontroller.GameController
import ru.sergey.domain.gamecontroller.GameControllerRepository
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_controller")

class GameControllerRepositoryImpl @Inject constructor (@ApplicationContext private val context: Context) : GameControllerRepository {

    // Ключи для DataStore
    private object PreferencesKeys {
        val STREAK_DAYS = intPreferencesKey("streak_days")
        val TOTAL_POINTS = intPreferencesKey("total_points")
        val POINTS_ON_TIME = intPreferencesKey("points_on_time")
        val TIME_START = stringPreferencesKey("time_start")
        val TIME_END = stringPreferencesKey("time_end")
    }

    // Функция для сохранения данных GameController
    override suspend fun saveGameController(gameController: GameController) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.STREAK_DAYS] = gameController.streakDays
            preferences[PreferencesKeys.TOTAL_POINTS] = gameController.totalPoints
            preferences[PreferencesKeys.POINTS_ON_TIME] = gameController.pointsOnTime
            preferences[PreferencesKeys.TIME_START] = gameController.timeStart
            preferences[PreferencesKeys.TIME_END] = gameController.timeEnd
        }
    }


    // Функция для получения GameController целиком (Flow)
    override  val gameControllerFlow: Flow<GameController> = context.dataStore.data.map { preferences ->
        GameController(
            streakDays = preferences[PreferencesKeys.STREAK_DAYS] ?: 0,
            totalPoints = preferences[PreferencesKeys.TOTAL_POINTS] ?: 0,
            pointsOnTime = preferences[PreferencesKeys.POINTS_ON_TIME] ?: 0,
            timeStart = preferences[PreferencesKeys.TIME_START] ?: "00.00.0000",
            timeEnd = preferences[PreferencesKeys.TIME_END] ?: "00.00.0000"
        )
    }
}