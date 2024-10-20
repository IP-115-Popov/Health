package ru.sergey.data.storage

import android.util.EventLogTags.Description
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.sergey.data.storage.TaskStorage.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class TaskStorage (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "taskId")
    val id: Int,
    val title: String,
    val description: String = "",
    val points: Int = 0,
    val targetPoints: Int = 100
) {
    companion object {
        const val TABLE_NAME = "tasks"
        const val ID = "id"
        const val TITLE = "title"
        const val Description = "description"
        const val POINTS = "points"
        const val TARGET_POINTS = "targetPoints"

    }
}