package ru.sergey.domain.task.models

data class Task(
    val id: Int,
    val title: String,
    val description: String = "",
    val points: Int = 0,
    val targetPoints: Int = 100,
    val measureUnit: String = ""
) {
    fun newBuilder(): Builder {
        return Builder()
            .setId(id)
            .setTitle(title)
            .setDescription(description)
            .setPoints(points)
            .setTargetPoints(targetPoints)
            .setMeasureUnit(measureUnit)
    }

    class Builder {
        var id: Int = 0
        var title: String = ""
        var description: String = ""
        var points: Int = 0
        var targetPoints: Int = 100
        var measureUnit: String = ""

        fun setId(id: Int): Builder {
            this.id = id
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setPoints(points: Int): Builder {
            this.points = points
            return this
        }

        fun setTargetPoints(targetPoints: Int): Builder {
            this.targetPoints = targetPoints
            return this
        }

        fun setMeasureUnit(measureUnit: String): Builder {
            this.measureUnit = measureUnit
            return this
        }


        fun build(): Task {
            return Task(
                id = id,
                title = title,
                description = description,
                points = points,
                targetPoints = targetPoints,
                measureUnit = measureUnit
            )
        }
    }
}