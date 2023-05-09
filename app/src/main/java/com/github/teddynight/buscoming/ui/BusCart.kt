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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.ui.theme.BusComingTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.ui.NearbyScreenViewModel

@Composable
fun timeInCart(bId: String, viewModel: NearbyScreenViewModel = viewModel()){
    val state = viewModel.arrivals.observeAsState()
    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(4.dp)) {
        Text(
            "9",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "分",
            fontSize = 16.sp,
            color = Color.Gray);
    }
}

@Composable
fun busCart(busPair: List<Bus>) {
    var direction = remember { mutableStateOf(true) }
    val bus = if(direction.value) busPair[0] else busPair[1]
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .clickable { }) {
        Column() {
            bigCartText(bus.name)
            smallCartText("终点站："+bus.endSn)
        }
        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()) {
            timeInCart(bus.id);
            Divider(color = Color.Gray,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .width(1.dp))
            Box(modifier = Modifier
                .size(36.dp)
                .padding(4.dp)
                .align(Alignment.CenterVertically)
                .clickable { direction.value = !direction.value }) {
                val stationIcon = painterResource(R.drawable.exchange)
                Image(stationIcon,"切换方向",
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center)
            }
        }
    }
}