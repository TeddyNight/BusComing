package com.github.teddynight.buscoming

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.ViewModel
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.ui.NearbyScreenViewModel
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun topStationCart(station: Station, viewModel: NearbyScreenViewModel = viewModel()) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0x7f7f7f))
            .fillMaxWidth()
            .clickable { viewModel.refreshStn(station.id) }) {
        Box(modifier = Modifier
            .size(24.dp)
            .padding(4.dp)) {
            val stationIcon = painterResource(R.drawable.destination)
            Image(stationIcon,"车站",
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center)
        }

        mediumCartText(station.name)
    }
}

@Composable
fun stationCart(station: Station, viewModel: NearbyScreenViewModel = viewModel()) {
    val _buses = viewModel.buses.observeAsState()
    val sid = viewModel.sid.observeAsState()
    val status = viewModel.stnStatus.observeAsState()
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxHeight()) {
        topStationCart(station)
        Column() {
            if (sid.value!! == station.id) {
                if (!status.value!!) {
                    putCenter {
                        CircularProgressIndicator()
                    }
                }
                else {
                    val buses = _buses.value
                    if (buses != null) {
                        buses.forEach() {
                            busCart(it)
                        }
                    }
                }
            }
        }
    }
}