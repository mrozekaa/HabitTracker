package com.mrozeka.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.mrozeka.habittracker.ui.ChatScreen
import com.mrozeka.habittracker.ui.MainViewModel
import com.mrozeka.habittracker.ui.MainViewModelFactory
import com.mrozeka.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as HabitTrackerApplication).repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.observe(this) {
            when (it) {
                MainViewModel.State.Loading -> {/*TODO Add loading view*/
                    viewModel.habits.observe(this) { habits ->
                        viewModel.loadData(habits)
                    }
                }

                is MainViewModel.State.StartConversation -> {
                    viewModel.emitNextHabitMessage()
                }

                else -> {/* Do nothing */
                }
            }
        }

        setContent {
            val conversation = viewModel.conversation.collectAsState()
            HabitTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TopAppBar(
                            title = { Text(text = "Habit Tracker :)") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                        ChatScreen(
                            modifier = Modifier,
                            conversation = conversation.value
                        ) { isAccomplished ->
                            viewModel.setHabitInfo(isAccomplished)
                            viewModel.emitNextHabitMessage()
                        }
                    }
                }
            }
        }
    }
}