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
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.ui.theme.BusComingTheme

@Composable
fun topStationCart(station: String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0x7f7f7f))
            .fillMaxWidth()
            .clickable { }) {
        Box(modifier = Modifier
            .size(24.dp)
            .padding(4.dp)) {
            val stationIcon = painterResource(R.drawable.destination)
            Image(stationIcon,"车站",
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center)
        }

        smallCartText(station)
    }
}

@Composable
fun stationCart(station: Station) {
    Column(modifier = Modifier
        .padding(4.dp)
        .height(IntrinsicSize.Min)) {
        topStationCart(station.name)
        Column() {
            station.buses.forEach() {
                busCart(busPair = it)
            }
        }
    }
}