package com.mrozeka.habittracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatHabitItem(text:String,
    positiveButtonClicked: () -> Unit,
    negativeButtonClicked: () -> Unit
) {
    TextChatItem(text)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
            onClick = positiveButtonClicked
        ) {
            Text("Yes")
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
            onClick = negativeButtonClicked
        ) {
            Text("No")
        }
    }
}