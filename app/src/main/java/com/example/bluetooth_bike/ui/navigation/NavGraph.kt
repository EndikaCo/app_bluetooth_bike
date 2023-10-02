package com.example.bluetooth_bike.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetooth_bike.ui.viewmodels.BluetoothViewModel
import com.example.bluetooth_bike.ui.bluetooth.DevicesScreen
import com.example.bluetooth_bike.ui.screens.ConnectedScreen

sealed class Routes(val routes:String){
    object DevicesScreen: Routes("p1")
    object ConnectedScreen: Routes("p2")
}

@Composable
fun NavGraph() {
    val navigationController = rememberNavController()
    val viewModel: BluetoothViewModel = viewModel()

    NavHost(
        navController = navigationController,
        startDestination = Routes.DevicesScreen.routes
    ) {
        composable(Routes.DevicesScreen.routes) { DevicesScreen(viewModel) }
        composable(Routes.ConnectedScreen.routes) { ConnectedScreen(viewModel) }
    }
}

