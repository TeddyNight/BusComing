package com.github.teddynight.buscoming.ui.nearby

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun timeInCart(waitingTime: String){
    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(4.dp)) {
        Text(
            text = waitingTime,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "分",
            fontSize = 16.sp,
            color = Color.Gray);
    }
}