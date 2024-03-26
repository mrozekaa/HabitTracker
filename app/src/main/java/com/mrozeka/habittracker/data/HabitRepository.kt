package com.mrozeka.habittracker.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: Flow<List<Habit>> = habitDao.getHabits()

    @WorkerThread
    suspend fun insert(habit: Habit) {
        habitDao.insert(habit)
    }
}