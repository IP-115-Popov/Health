package ru.sergey.data.step

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
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

    private val PREFS_NAME = "step_prefs"
    private val KEY_STEPS_START = "steps_start_of_day"
    private val KEY_DATE = "date_of_start"

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private var stepsAtStartOfDay: Long = 0
    private var currentSteps: Long = 0

    init {
        loadStepsData()
    }

    /**
     * Загружает сохранённые данные из SharedPreferences
     * Проверяет дату и при необходимости сбрасывает шаги на начало дня
     */
    private fun loadStepsData() {
        stepsAtStartOfDay = prefs.getLong(KEY_STEPS_START, 0L)
        val savedDate = prefs.getString(KEY_DATE, "") ?: ""
        val today = getTodayDateString()

        if (savedDate != today) {
            // Новый день — сбрасываем стартовые шаги
            stepsAtStartOfDay = currentSteps
            saveStepsData()
        }
    }

    /**
     * Сохраняет данные в SharedPreferences
     */
    private fun saveStepsData() {
        prefs.edit()
            .putLong(KEY_STEPS_START, stepsAtStartOfDay)
            .putString(KEY_DATE, getTodayDateString())
            .apply()
    }

    /**
     * Возвращает текущую дату в формате yyyyMMdd
     */
    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date())
    }

    /**
     * Обновляет текущее значение шагов, вызывается при получении новых данных с датчика
     */
    fun updateCurrentSteps(steps: Long) {
        currentSteps = steps
        checkDateAndResetIfNeeded()
    }

    /**
     * Проверяет дату, если сменился день — обновляет стартовые шаги
     */
    private fun checkDateAndResetIfNeeded() {
        val today = getTodayDateString()
        val savedDate = prefs.getString(KEY_DATE, "") ?: ""

        if (savedDate != today) {
            stepsAtStartOfDay = currentSteps
            saveStepsData()
        }
    }

    /**
     * Возвращает количество шагов за текущий день
     */
    fun getStepsForToday(): Long {
        val steps = currentSteps - stepsAtStartOfDay
        return if (steps >= 0) steps else 0L
    }

    /**
     * Сбрасывает статистику (например, по нажатию кнопки)
     */
    fun resetSteps() {
        stepsAtStartOfDay = currentSteps
        saveStepsData()
    }
}
