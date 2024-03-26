package com.mrozeka.habittracker.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatMoodItem(text:String, onPositiveButtonClicked: () -> Unit, onNegativeButtonClicked: () -> Unit) {
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
            onClick = onPositiveButtonClicked

        ) {
            Text("Great!")
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
            onClick = onPositiveButtonClicked
        ) {
            Text("OK")
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
            onClick = onNegativeButtonClicked
        ) {
            Text("Bad")
        }
    }
}