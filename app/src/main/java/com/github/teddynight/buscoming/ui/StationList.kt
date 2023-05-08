package com.github.teddynight.buscoming

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.distinctUntilChanged
import com.github.teddynight.buscoming.ui.StationListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun stationList(viewModel: StationListViewModel = viewModel()) {
    val stationState = viewModel.stations.distinctUntilChanged().observeAsState()
    LazyColumn() {
        val stations = viewModel.stations.value!!
        items(stations) {
            stationCart(station = it.name)
        }
    }
}