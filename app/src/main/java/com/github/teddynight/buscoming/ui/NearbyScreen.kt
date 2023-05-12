package com.github.teddynight.buscoming.ui

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.stationList
import com.github.teddynight.buscoming.ui.theme.putCenter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.utlis.Location
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun nearbyScreen(viewModel: NearbyScreenViewModel = viewModel()) {
    val networkState = viewModel.activeNetworkInfoLiveData.observeAsState()
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        // run only once
        LaunchedEffect(Unit) {
            viewModel.getLocation()
            viewModel.refresh()
        }
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
            if (networkState.value == null) {
                putCenter {
                    Column() {
                        Text("网络不可用")
                    }
                }
            }
            else {
                stationList()
            }
        }
    } else {
        putCenter {
            Column() {
                Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                    Text("获取定位权限")
                }
            }
        }
    }
}