package com.example.bluetooth_bike.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetooth_bike.ui.bluetooth.BluetoothViewModel
import com.example.bluetooth_bike.ui.bluetooth.DevicesScreen
import com.example.bluetooth_bike.ui.login.LoginScreen
import com.example.bluetooth_bike.ui.signup.SignUpScreen

sealed class Routes(val routes:String){
    object DevicesScreen: Routes("p1")
    object LoginScreen: Routes("p2")
    object SignInScreen: Routes("p3")
}

@Composable
fun Navigation(viewModel: BluetoothViewModel) {
    val navigationController = rememberNavController()


    NavHost(
        navController = navigationController,
        startDestination = Routes.DevicesScreen.routes
    ) {
        composable(Routes.DevicesScreen.routes) {

            //val viewModel: BluetoothViewModel = viewModel()
            // val viewModel: BluetoothViewModel by viewModels()
            val state by viewModel.state.collectAsState()

            DevicesScreen(navigationController,state = state,
            onStartScan = viewModel::startScan,
            onStopScan = viewModel::stopScan)
        }
        composable(Routes.LoginScreen.routes) { LoginScreen(navigationController) }
        composable(Routes.SignInScreen.routes) { SignUpScreen(navigationController) }
    }
}

