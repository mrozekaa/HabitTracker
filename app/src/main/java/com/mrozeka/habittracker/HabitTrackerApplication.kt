package com.mrozeka.habittracker

import android.app.Application
import com.mrozeka.habittracker.data.HabitRepository
import com.mrozeka.habittracker.data.HabitRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HabitTrackerApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { HabitRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { HabitRepository(database.habitDao()) }
}