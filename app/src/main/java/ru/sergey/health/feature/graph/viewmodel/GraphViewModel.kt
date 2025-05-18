package ru.sergey.health.feature.graph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sergey.domain.task.models.Points
import ru.sergey.domain.task.usecase.GetPointsUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    val getPointsUseCase: GetPointsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GraphUiState())
    val state: StateFlow<GraphUiState> = _state.asStateFlow()

    private var points = emptyList<Points>()

    fun loadData(taskId: Int) = viewModelScope.launch(Dispatchers.IO) {
        points = getPointsUseCase.execute(taskId).first()
        setGraphMode(state.value.graphMode)
    }


    private suspend fun getDailyPointsGrowth() = withContext(Dispatchers.Main) {
        _state.update {
            it.copy(pointsList = fillMissingDates(calculateDailyPoints(points)))
        }
    }

    private suspend fun getGrowthRate() = withContext(Dispatchers.Main) {
        _state.update {
            it.copy(
                pointsList = calculateProgressVelocityPoints(
                    fillMissingDatesWithPreviousValue(
                        points
                    )
                )
            )
        }
    }

    private suspend fun getTotalNumberOfPoints() = withContext(Dispatchers.Main) {
        _state.update {
            it.copy(pointsList = (points))
        }
    }

    private suspend fun getTotalNumberOfPointsWithMissedDays() = withContext(Dispatchers.Main) {
        _state.update {
            it.copy(pointsList = fillMissingDatesWithPreviousValue(points))
        }
    }

    fun setGraphMode(graphMode: GraphMode) = viewModelScope.launch {
        _state.update {
            it.copy(graphMode = graphMode)
        }
        when (graphMode) {
            GraphMode.DailyPointsGrowth -> getDailyPointsGrowth()
            GraphMode.GrowthRate -> getGrowthRate()
            GraphMode.TotalNumberOfPoints -> getTotalNumberOfPoints()
            GraphMode.TotalNumberOfPointsWithMissedDays -> getTotalNumberOfPointsWithMissedDays()
        }
    }
}

/**
 * Заполняет пропущенные даты в списке Points, добавляя Points с points = 0.
 *
 * @param points Исходный список Points (может быть неотсортированным).
 * @return Новый список Points, содержащий все даты от самой ранней до самой поздней,
 *         с добавленными Points с points = 0 для пропущенных дат.  Исходный список не изменяется.
 *
 * @throws IllegalArgumentException Если даты в списке Points содержат ошибки форматирования.
 */
fun fillMissingDates(points: List<Points>): List<Points> {
    if (points.isEmpty()) {
        return emptyList() // Если список пуст, ничего не нужно заполнять
    }

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // Преобразуем даты в LocalDate для удобства работы
    val pointsWithDates: List<Pair<LocalDate, Points>> = points.map { point ->
        try {
            LocalDate.parse(point.date, formatter) to point
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "Неверный формат даты: ${point.date}. Ожидается dd.MM.yyyy",
                e
            )
        }
    }

    // Сортируем список по дате
    val sortedPoints = pointsWithDates.sortedBy { it.first }

    val startDate = sortedPoints.first().first
    val endDate = sortedPoints.last().first

    val filledPoints = mutableListOf<Points>()

    var currentDate = startDate
    while (!currentDate.isAfter(endDate)) {
        val existingPoint = sortedPoints.find { it.first == currentDate }

        if (existingPoint != null) {
            // Дата уже есть в исходном списке
            filledPoints.add(existingPoint.second)
        } else {
            // Дата пропущена, добавляем Points с points = 0
            filledPoints.add(
                Points(
                    pointId = -1, // Указываем, что это добавленная точка (можно использовать любое значение, которое означает "отсутствует")
                    taskId = 0,
                    date = currentDate.format(formatter),
                    points = 0
                )
            )
        }

        currentDate = currentDate.plusDays(1)
    }

    return filledPoints.toList() // Возвращаем новый неизменяемый список
}

/**
 * Вычисляет скорость прогресса (производную) и возвращает ее в виде списка Points.
 * Каждый Point в результирующем списке представляет скорость прогресса между двумя последовательными точками
 * в исходном списке.
 *
 * @param points Список объектов Points, отсортированных по дате.
 * @return Список объектов Points, представляющих скорость прогресса (очков в день).
 *         Если список points содержит меньше двух элементов, возвращает пустой список.
 *
 * @throws IllegalArgumentException Если даты в списке points не отсортированы или содержат ошибки форматирования.
 */
fun calculateProgressVelocityPoints(points: List<Points>): List<Points> {
    if (points.size < 2) {
        return emptyList() // Невозможно вычислить скорость, если меньше двух точек
    }

    val velocityPoints = mutableListOf<Points>()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    for (i in 1 until points.size) {
        val point1 = points[i - 1]
        val point2 = points[i]

        val date1: LocalDate
        val date2: LocalDate

        try {
            date1 = LocalDate.parse(point1.date, formatter)
            date2 = LocalDate.parse(point2.date, formatter)
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "Неверный формат даты: ${point1.date} или ${point2.date}.  Ожидается dd.MM.yyyy",
                e
            )
        }

        if (date2.isBefore(date1)) {
            throw IllegalArgumentException("Данные должны быть отсортированы по дате.  Дата ${point2.date} предшествует дате ${point1.date}")
        }

        val daysBetween = ChronoUnit.DAYS.between(date1, date2)
        val pointsDifference = (point2.points - point1.points).toDouble()

        val velocity = pointsDifference / daysBetween

        // Создаем новый объект Points, представляющий скорость.
        // Дату можно взять как среднюю между date1 и date2, либо просто дату date2 (как в этом примере).
        // Важно выбрать подходящий способ представления даты для вашей задачи.
        velocityPoints.add(
            Points(
                pointId = i, // Используем индекс, чтобы можно было связать с исходными данными
                taskId = point1.taskId, // Берем taskId от одной из точек (можно выбрать любую)
                date = point2.date, // Используем дату второй точки
                points = velocity.toInt() // Скорость преобразуем в Int (можно округлить или использовать Double в Points)
            )
        )
    }

    return velocityPoints
}

/**
 * Заполняет пропущенные даты в списке Points, добавляя Points со значением points, равным значению из предыдущей даты.
 * Если предыдущей даты нет (т.е. для самой первой даты), то points = 0.
 *
 * @param points Исходный список Points (может быть неотсортированным).
 * @return Новый список Points, содержащий все даты от самой ранней до самой поздней,
 *         с добавленными Points, у которых points равно значению из предыдущей даты.
 *         Исходный список не изменяется.
 *
 * @throws IllegalArgumentException Если даты в списке Points содержат ошибки форматирования.
 */
fun fillMissingDatesWithPreviousValue(points: List<Points>): List<Points> {
    if (points.isEmpty()) {
        return emptyList() // Если список пуст, ничего не нужно заполнять
    }

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // Преобразуем даты в LocalDate для удобства работы
    val pointsWithDates: List<Pair<LocalDate, Points>> = points.map { point ->
        try {
            LocalDate.parse(point.date, formatter) to point
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "Неверный формат даты: ${point.date}. Ожидается dd.MM.yyyy",
                e
            )
        }
    }

    // Сортируем список по дате
    val sortedPoints = pointsWithDates.sortedBy { it.first }

    val startDate = sortedPoints.first().first
    val endDate = sortedPoints.last().first

    val filledPoints = mutableListOf<Points>()

    var currentDate = startDate
    var previousPointsValue = 0 // Значение points для первой даты, если она отсутствует

    while (!currentDate.isAfter(endDate)) {
        val existingPoint = sortedPoints.find { it.first == currentDate }

        if (existingPoint != null) {
            // Дата уже есть в исходном списке
            filledPoints.add(existingPoint.second)
            previousPointsValue = existingPoint.second.points // Обновляем previousPointsValue
        } else {
            // Дата пропущена, добавляем Points с points, равным значению из предыдущей даты
            filledPoints.add(
                Points(
                    pointId = -1, // Указываем, что это добавленная точка
                    taskId = 0,
                    date = currentDate.format(formatter),
                    points = previousPointsValue // Используем значение points из предыдущей даты
                )
            )
        }

        currentDate = currentDate.plusDays(1)
    }

    return filledPoints.toList() // Возвращаем новый неизменяемый список
}

/**
 * Преобразует список Points, где каждый элемент хранит сумму очков за все прошлые дни,
 * в список, где каждый элемент хранит только очки, полученные в этот день.
 *
 * @param points Исходный список Points, отсортированных по дате, где каждый элемент хранит сумму очков за все прошлые дни.
 * @return Новый список Points, где каждый элемент хранит только очки, полученные в этот день.
 *         Если список points содержит меньше одного элемента, возвращает пустой список.
 *
 * @throws IllegalArgumentException Если даты в списке points не отсортированы или содержат ошибки форматирования.
 */
fun calculateDailyPoints(points: List<Points>): List<Points> {
    if (points.isEmpty()) {
        return emptyList() // Если список пуст, то и делать ничего не нужно
    }

    val dailyPointsList = mutableListOf<Points>()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // Создаем LocalDate для первой точки
    val firstDate: LocalDate
    try {
        firstDate = LocalDate.parse(points[0].date, formatter)
    } catch (e: Exception) {
        throw IllegalArgumentException(
            "Неверный формат даты: ${points[0].date}.  Ожидается dd.MM.yyyy",
            e
        )
    }

    // Для первой точки все просто: ее очки и есть очки за этот день
    dailyPointsList.add(
        points[0].copy(points = points[0].points) // Создаем копию, чтобы не менять исходный объект
    )

    for (i in 1 until points.size) {
        val point = points[i]
        val previousPoint = points[i - 1]

        val date: LocalDate
        try {
            date = LocalDate.parse(point.date, formatter)
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "Неверный формат даты: ${point.date}.  Ожидается dd.MM.yyyy",
                e
            )
        }

        if (date.isBefore(LocalDate.parse(previousPoint.date, formatter))) {
            throw IllegalArgumentException("Данные должны быть отсортированы по дате. Дата ${point.date} предшествует дате ${previousPoint.date}")
        }

        // Вычисляем очки, полученные в этот день: разница между текущей суммой и предыдущей
        val dailyPoints = point.points - previousPoint.points

        // Добавляем новый Point с очками только за этот день
        dailyPointsList.add(
            point.copy(points = dailyPoints) // Создаем копию, чтобы не менять исходный объект
        )
    }

    return dailyPointsList.toList() // Возвращаем новый неизменяемый список
}

