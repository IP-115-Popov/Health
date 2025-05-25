package ru.sergey.data.step

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val repository = StepRepository(context)

    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    suspend fun updateCurrentSteps(totalSteps: Long) {
        repository.saveSteps(getTodayDateString(), totalSteps)
    }

    suspend fun getStepsForToday(): Long {
        return repository.getStepsForDay(data = getTodayDateString())
    }

    fun getStepsFlow() = repository.stepsMapFlow
}

private val Context.dataStore by preferencesDataStore(name = "step_stats_prefs")

@Singleton
class StepRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val STEPS_MAP_KEY = stringPreferencesKey("steps_map")

    private val gson = Gson()

    /**
     * Flow с картой дата -> шаги
     */
    val stepsMapFlow: Flow<Map<String, Long>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[STEPS_MAP_KEY] ?: ""
            if (json.isEmpty()) emptyMap()
            else deserializeMap(json)
        }

    /**
     * Сохраняет количество шагов за дату
     */
    suspend fun saveSteps(date: String, totalSteps: Long) {
        context.dataStore.edit { preferences ->
            val currentMap = preferences[STEPS_MAP_KEY]?.let { deserializeMap(it) } ?: emptyMap()

            val oldSteps = currentMap.map { it.value }.sum()
            val deltaSteps = totalSteps - oldSteps

            val newMap = currentMap.toMutableMap().apply {
                this[date] = this.getOrDefault(date, 0L) + deltaSteps
            }
            preferences[STEPS_MAP_KEY] = serializeMap(newMap)
        }
    }

    suspend fun getStepsForDay(data: String) =
        context.dataStore.data
            .map { preferences ->
                val json = preferences[STEPS_MAP_KEY] ?: ""
                if (json.isEmpty()) 0L
                else deserializeMap(json).getOrDefault(data, 0L)
            }.first()


    private fun serializeMap(map: Map<String, Long>): String {
        return gson.toJson(map)
    }

    private fun deserializeMap(json: String): Map<String, Long> {
        val type = object : TypeToken<Map<String, Long>>() {}.type
        return gson.fromJson(json, type)
    }
}