package ru.sergey.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskStorage::class, TaskPointsEntity::class], version = 1
)
abstract class TaskRoomDatabase : RoomDatabase() {

    abstract fun TaskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "task_database_1.db"

        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getInstance(context: Context): TaskRoomDatabase {
            INSTANCE?.let { return it }
            val application = context.applicationContext
            synchronized(this) {
                INSTANCE?.let { return it }
                val appDb =
                    Room.databaseBuilder(application, TaskRoomDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration().build()
                INSTANCE = appDb
                return appDb
            }
        }
    }
}