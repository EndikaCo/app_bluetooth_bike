package com.example.bluetooth_bike.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetooth_bike.ui.login.LoginScreen
import com.example.bluetooth_bike.ui.signup.SignUpScreen

sealed class Routes(val routes:String){
    object DevicesScreen: Routes("p1")
    object LoginScreen: Routes("p2")
    object SignInScreen: Routes("p3")
}

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.SignInScreen.routes
    ) {
        composable(Routes.DevicesScreen.routes) { DevicesScreen(navigationController) }
        composable(Routes.LoginScreen.routes) { LoginScreen(navigationController) }
        composable(Routes.SignInScreen.routes) { SignUpScreen(navigationController) }
    }
}

