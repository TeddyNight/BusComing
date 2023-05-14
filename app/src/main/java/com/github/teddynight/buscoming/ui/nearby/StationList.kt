package com.github.teddynight.buscoming.ui.nearby

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun stationList(viewModel: NearbyScreenViewModel = viewModel(),
                navController: NavHostController) {
    val _stations = viewModel.stations.distinctUntilChanged().observeAsState()
    val stations = _stations.value
    if (stations != null) {
        LazyColumn() {
            items(stations) {
                stationCart(station = it,navController = navController)
            }
        }
    }
    else {
        putCenter() {
            CircularProgressIndicator()
        }
    }
}