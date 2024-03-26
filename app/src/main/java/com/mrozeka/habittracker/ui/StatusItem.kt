package com.mrozeka.habittracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrozeka.habittracker.data.Message
import com.mrozeka.habittracker.ui.theme.PurpleGrey80
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

@Composable
fun StatusItem(text: String, habits: List<Message>) {
    TextChatItem(text)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = 48f,
                        bottomEnd = 0f
                    )
                )
                .background(PurpleGrey80)
                .padding(16.dp)
        ) {
            Column {
                habits.filter { it.habit != null }.forEach { msg ->
                    val durationInMillis =
                        Calendar.getInstance().timeInMillis - msg.habit!!.startTimeInMillis
                    val days = durationInMillis.milliseconds.toLong(DurationUnit.DAYS)
                    Text(text = msg.habit.type.name, fontSize = 30.sp)
                    if (days > 0) {
                        Text(text = "${msg.positiveResponse} You've maintaining your habit for $days days")
                    } else {
                        Text(text = msg.negativeResponse)
                    }
                }
            }

        }
    }
}