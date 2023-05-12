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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.ui.NearbyScreenViewModel
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun stationList(viewModel: NearbyScreenViewModel = viewModel()) {
    val _stations = viewModel.stations.distinctUntilChanged().observeAsState()
    val stations = _stations.value
    if (stations != null) {
        LazyColumn() {
            items(stations) {
                stationCart(station = it)
            }
        }
    }
    else {
        putCenter() {
            CircularProgressIndicator()
        }
    }
}