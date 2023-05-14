package com.github.teddynight.buscoming

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.teddynight.buscoming.service.NotificationService
import com.github.teddynight.buscoming.ui.bus.busScreen
import com.github.teddynight.buscoming.ui.nearby.nearbyScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this,NotificationService::class.java))
        }
        else {
            startService(Intent(this,NotificationService::class.java))
        }
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(navController = navController, startDestination = "nearby") {
                    composable("nearby") { nearbyScreen(navController = navController) }
                    composable("bus/{line0}/{line1}") { busScreen(navController = navController) }
                }
            }
        }
    }
}