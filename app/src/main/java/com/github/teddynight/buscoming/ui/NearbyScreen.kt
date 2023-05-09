package com.github.teddynight.buscoming.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.stationList
import com.github.teddynight.buscoming.ui.theme.putCenter
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun nearbyScreen(viewModel: NearbyScreenViewModel = viewModel()) {
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
                Column() {
                    Text("加载失败",)
                    Button(onClick = { viewModel.refresh() }) {
                        Text("重新加载")
                    }
                }
            }
        }
    }
}