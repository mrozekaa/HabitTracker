package com.mrozeka.habittracker.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mrozeka.habittracker.data.Message
import com.mrozeka.habittracker.data.MessageType

@Composable
fun ChatScreen(
    conversation: List<Message>,
    modifier: Modifier,
    onResponseSendListener: (isHabitAccomplished: Boolean) -> Unit
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (messages) = createRefs()
        val listState = rememberLazyListState()
        LaunchedEffect(conversation.size) {
            listState.animateScrollToItem(conversation.size)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messages) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(conversation) {
                Log.v("HT","${it.type} ${it.text}")
                when (it.type) {
                    MessageType.WELCOME -> {
                        ChatMoodItem(
                            it.text,
                            onPositiveButtonClicked = { onResponseSendListener(true) },
                            onNegativeButtonClicked = { onResponseSendListener(true) })
                    }

                    MessageType.SUMMARY -> {
                        StatusItem(it.text, conversation)
                    }

                    MessageType.HABIT -> {
                        ChatHabitItem(
                            it.text,
                            positiveButtonClicked = { onResponseSendListener(true) },
                            negativeButtonClicked = { onResponseSendListener(false) })
                    }
                }
            }
        }
    }
}