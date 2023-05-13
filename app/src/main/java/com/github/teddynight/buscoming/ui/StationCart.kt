package com.github.teddynight.buscoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.teddynight.buscoming.data.model.Station
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.ui.NearbyScreenViewModel
import com.github.teddynight.buscoming.ui.busList
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun topStationCart(station: Station, viewModel: NearbyScreenViewModel = viewModel()) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0x7f7f7f))
            .fillMaxWidth()
//            .clickable { viewModel.refreshStn(station.id) }
            .clickable { viewModel.getStn(station.id) }
    ) {
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
    val sid = viewModel.sid.observeAsState()
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxHeight()) {
        topStationCart(station)
        Column() {
            if (sid.value != null && sid.value!! == station.id) {
                busList()
            }
        }
    }
}