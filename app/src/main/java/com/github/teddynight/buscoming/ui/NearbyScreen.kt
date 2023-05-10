package com.github.teddynight.buscoming.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.stationList
import com.github.teddynight.buscoming.ui.theme.putCenter
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun nearbyScreen(viewModel: NearbyScreenViewModel = viewModel()) {
    val state = viewModel.status.observeAsState()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("附近的车") },
        )
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.refresh() }) {
                Icon(Icons.Rounded.Refresh,"refresh")
            }
        }) {
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
                        Text("加载失败")
                    }
                }
            }
        }
    }
}