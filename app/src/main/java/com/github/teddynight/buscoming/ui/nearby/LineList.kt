package com.github.teddynight.buscoming.ui.nearby

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun lineList(viewModel: NearbyScreenViewModel = viewModel(), navController: NavHostController) {
    val lines = viewModel.lines.observeAsState()
    if (lines.value == null) {
        putCenter() {
            CircularProgressIndicator()
        }
    }
    else {
        for (i in 0 until lines.value!!.size) {
            lineCart(i,navController = navController)
        }
    }
}