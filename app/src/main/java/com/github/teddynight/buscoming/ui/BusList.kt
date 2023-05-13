package com.github.teddynight.buscoming.ui

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.teddynight.buscoming.busCart
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun busList(viewModel: NearbyScreenViewModel = viewModel()) {
    val buses = viewModel.buses.observeAsState()
    if (buses.value == null) {
        putCenter() {
            CircularProgressIndicator()
        }
    }
    else {
        for (i in 0 until buses.value!!.size) {
            busCart(i)
        }
    }
}