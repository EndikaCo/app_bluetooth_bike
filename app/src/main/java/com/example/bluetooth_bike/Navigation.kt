package com.example.bluetooth_bike

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetooth_bike.login.LoginScreen
import com.example.bluetooth_bike.login.LoginViewModel

sealed class Routes(val routes:String){
    object DevicesScreen:Routes("p1")
    object LoginScreen:Routes("p2")

}

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.LoginScreen.routes
    ) {
        composable(Routes.DevicesScreen.routes) { DevicesScaffold(navigationController) }
        composable(Routes.LoginScreen.routes) { LoginScreen(navigationController, LoginViewModel()) }
    }
}

