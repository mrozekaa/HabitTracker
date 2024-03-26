package com.mrozeka.habittracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey @ColumnInfo(name = "type") val type: Type,
    @ColumnInfo(name = "startTimeInMillis") var startTimeInMillis: Long,
)

enum class Type(val value: Int) {
    SOBER(1),
    WAKE_UP(2),
    WATER(3),
    SUGAR(4),
}