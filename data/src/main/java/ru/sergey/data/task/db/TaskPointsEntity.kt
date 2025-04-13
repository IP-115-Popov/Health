package ru.sergey.data.task.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.sergey.data.task.db.TaskPointsEntity.Companion.TABLE_NAME
import ru.sergey.data.task.db.TaskPointsEntity.Companion.TASK_ID
import ru.sergey.data.task.db.TaskStorage.Companion.ID
import ru.sergey.domain.task.models.Points


@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TaskStorage::class,
            parentColumns = [ID], // Указываем столбец taskId в родительской таблице
            childColumns = [TASK_ID],  // Указываем столбец taskId в дочерней таблице
            onDelete = ForeignKey.CASCADE // Указываем, что делать при удалении задачи
        )
    ]
)
class TaskPointsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = POINT_ID)
    val pointId: Int = 0,

    @ColumnInfo(name = TASK_ID)
    val taskId: Int,

    @ColumnInfo(name = DATA)
    val date: String,

    @ColumnInfo(name = POINTS)
    val points: Int,
) {
    companion object {
        const val TABLE_NAME = "task_points"
        const val POINT_ID = "pointId"
        const val TASK_ID = "date"
        const val DATA = "description"
        const val POINTS = "points"

        fun toEntity(points: Points): TaskPointsEntity {
            return TaskPointsEntity(
                pointId = points.pointId,
                taskId = points.taskId,
                date = points.date,
                points = points.points
            )
        }

        fun fromEntity(taskPointsEntity: TaskPointsEntity): Points {
            return Points(
                pointId = taskPointsEntity.pointId,
                taskId = taskPointsEntity.taskId,
                date = taskPointsEntity.date,
                points = taskPointsEntity.points
            )
        }
    }
}