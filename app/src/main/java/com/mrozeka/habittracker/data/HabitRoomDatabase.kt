package com.mrozeka.habittracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Habit::class], version = 1)
abstract class HabitRoomDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val soberHabit = Habit(Type.SOBER, 1709506800000)
                    val wakeUpHabit = Habit(Type.WAKE_UP, 1710284400000)
                    val waterHabit = Habit(Type.WATER, 1711148400000)
                    val sugarHabit = Habit(Type.SUGAR, 1711148400000)
                    database.habitDao().insertAll(listOf(soberHabit,wakeUpHabit,waterHabit,sugarHabit))
                 }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HabitRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): HabitRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitRoomDatabase::class.java,
                    "habit_database"
                ).addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}