package com.example.bluetooth_bike

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesScaffold(navigationController: NavHostController) {

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { TopAppBar(navigationController) },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = { FloatingActionButton(snackBarHostState) },
        content = { innerPadding -> DevicesContent(innerPadding) }
    )
}

@Composable
fun DevicesContent(innerPadding: PaddingValues) {

    LazyColumn(
        Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

    }
}

@Composable
fun FloatingActionButton(snackBarHostState: SnackbarHostState) {

    val scope = rememberCoroutineScope()

    var clickCount by remember { mutableIntStateOf(0) }
    ExtendedFloatingActionButton(
        onClick = {
            // show snack-bar as a suspend function
            scope.launch {
                snackBarHostState.showSnackbar(
                    "Snack bar # ${++clickCount}"
                )
            }
        }
    ) { Text("Show snack-bar") }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navHostController: NavHostController) {
    TopAppBar(
        title = { Text("Connect device") },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO navHostController.navigate */  }
            ) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Go back")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO: settings */  }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        },
    )
}
