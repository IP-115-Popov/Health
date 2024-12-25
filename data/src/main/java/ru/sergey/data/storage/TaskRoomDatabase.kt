package ru.sergey.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskStorage::class, TaskPointsEntity::class], version = 1
)
abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun TaskDao(): TaskDao

    companion object {
        fun buildDatabase(context: Context, dbName: String) : TaskRoomDatabase {
            return Room.databaseBuilder(context, TaskRoomDatabase::class.java , dbName).build()
        }
    }
}