package com.github.teddynight.buscoming.ui.nearby

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.github.teddynight.buscoming.data.model.Station
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.github.teddynight.buscoming.R

@Composable
fun topStationCart(station: Station, viewModel: NearbyScreenViewModel = viewModel()) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0x7f7f7f))
            .fillMaxWidth()
//            .clickable { viewModel.refreshStn(station.id) }
            .clickable { viewModel.getStn(station.id) }
    ) {
        Icon(ImageVector.vectorResource(R.drawable.station),"车站")

        mediumCartText(station.name)
    }
}

@Composable
fun stationCart(station: Station, viewModel: NearbyScreenViewModel = viewModel(),
                navController: NavHostController) {
    val sid = viewModel.sid.observeAsState()
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxHeight()) {
        topStationCart(station)
        Column() {
            if (sid.value != null && sid.value!! == station.id) {
                lineList(navController = navController)
            }
        }
    }
}