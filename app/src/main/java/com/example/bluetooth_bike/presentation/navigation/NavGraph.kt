package com.example.bluetooth_bike.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetooth_bike.presentation.screens.BikeScreen
import com.example.bluetooth_bike.presentation.screens.ProgressBarScreen
import com.example.bluetooth_bike.presentation.screens.DevicesScreen
import com.example.bluetooth_bike.presentation.viewmodels.BluetoothViewModel

sealed class Routes(val routes: String) {
    object DevicesScreen : Routes("p1")
    object ChatScreen : Routes("p2")
    object LoadingScreen : Routes("p3")
}

@Composable
fun NavGraph() {
    val navigationController = rememberNavController()
    val viewModel: BluetoothViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val activity = LocalContext.current as Activity

    LaunchedEffect(state) {
        if(state.isConnected)
            navigationController.navigate(Routes.ChatScreen.routes)
        else if (state.isConnecting)
            navigationController.navigate(Routes.LoadingScreen.routes)
        else
            navigationController.navigate(Routes.DevicesScreen.routes)
    }

    NavHost(
        navController = navigationController,
        startDestination = Routes.DevicesScreen.routes
    ) {
        composable(Routes.DevicesScreen.routes) {
            DevicesScreen(
                state = state,
                onScanClick = viewModel::scanToggle,
                onDeviceClick = viewModel::connectToDevice,
                onCloseClick = { activity.finish() }
            )
        }

        composable(Routes.ChatScreen.routes) {
            BikeScreen(
                uiState = state,
                onDisconnect = viewModel::disconnectFromDevice,
                onStartClick = { viewModel.sendData("STR") },
                onLightClick = { viewModel.sendData("LON") }
            )
        }

        composable(Routes.LoadingScreen.routes) {
            ProgressBarScreen(onCancelClick = viewModel::cancelServer)
        }
    }
}
