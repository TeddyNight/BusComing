package com.github.teddynight.buscoming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.teddynight.buscoming.ui.theme.BusComingTheme

@Composable
fun smallCartText(content: String) {
    Text(text = content,
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = Modifier.padding(4.dp));
}

@Composable
fun mediumCartText(content: String) {
    Text(text = content,
        fontSize = 18.sp,
        modifier = Modifier.padding(4.dp));
}

@Composable
fun bigCartText(content: String) {
    Text(text = content,
        fontSize = 22.sp,
        modifier = Modifier.padding(4.dp));
}