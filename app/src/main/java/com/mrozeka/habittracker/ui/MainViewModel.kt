package com.mrozeka.habittracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mrozeka.habittracker.data.Habit
import com.mrozeka.habittracker.data.HabitRepository
import com.mrozeka.habittracker.data.Message
import com.mrozeka.habittracker.data.MessageType
import com.mrozeka.habittracker.data.Type
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(private val repository: HabitRepository) : ViewModel() {

    val habits = repository.allHabits.asLiveData()
    private var habitsConversation: MutableList<Message> = mutableListOf()

    val state: LiveData<State>
        get() = _state
    private val _state = MutableLiveData<State>()

    val conversation: StateFlow<List<Message>>
        get() = _conversation
    private val _conversation = MutableStateFlow(
        emptyList<Message>()
    )

    init {
        _state.value = State.Loading
    }

    fun loadData(habits: List<Habit>) {
        if (habits.isEmpty()) return
        habitsConversation.add(
            Message(
                text = "Hi! How are you feeling?",
                type = MessageType.WELCOME
            )
        )

        habitsConversation.addAll(habits.map { habit ->
            Message(
                when (habit.type) {
                    Type.SOBER -> "Have you stayed sober?"
                    Type.SUGAR -> "Have you  been waking up early?"
                    Type.WATER -> "Have you been drinking water?"
                    Type.WAKE_UP -> "Have you been avoid sugar?"
                },
                MessageType.HABIT,
                habit,
                "That's cool!",
                "No worry, change is coming today !:)"
            )
        })
        habitsConversation.add(
            Message(
                type = MessageType.SUMMARY,
                text = "CONGRATULATION!"
            )
        )
        if (_state.value == State.Loading) {
            _state.value = State.StartConversation
        }
    }

    private fun getNextHabitQuestion(): Message? {
        return if (habitsConversation.isNotEmpty() && habitsConversation.size > conversation.value.size)
            habitsConversation[conversation.value.size] else null
    }

    fun setHabitInfo(isAccomplished: Boolean) {
        conversation.value.last().habit?.let { habit ->
            viewModelScope.launch {
                if (!isAccomplished || habit.startTimeInMillis < 0) {
                    habit.startTimeInMillis = Calendar.getInstance().timeInMillis
                }
                repository.insert(habit)
            }
        }
    }

    fun emitNextHabitMessage() {
        getNextHabitQuestion()?.let { msg ->

            viewModelScope.launch {
                delay(500)
                _conversation.emit(_conversation.value + msg)
            }
        }
    }

    sealed class State {
        data object Loading : State()
        data object StartConversation : State()
    }
}

class MainViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}