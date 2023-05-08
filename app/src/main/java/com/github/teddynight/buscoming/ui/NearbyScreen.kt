package com.github.teddynight.buscoming.ui

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.stationList
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun nearbyScreen(viewModel: NearbyScreenViewModel = NearbyScreenViewModel()) {
    val state = viewModel.status.observeAsState()
    when (state.value) {
        BusApiStatus.LOADING -> {
            putCenter() {
                CircularProgressIndicator()
            }
        }
        BusApiStatus.DONE -> {
            stationList()
        }
        BusApiStatus.ERROR -> {
            putCenter() {
                Text("加载失败")
            }
        }
    }
}