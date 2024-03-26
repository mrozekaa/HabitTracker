package com.mrozeka.habittracker.data

class Message(
    val text: String,
    val type: MessageType,
    val habit: Habit? = null,
    val positiveResponse: String = "",
    val negativeResponse: String = ""
)

enum class MessageType(val value: Int) {
    WELCOME(0),
    HABIT(1),
    SUMMARY(2),
}