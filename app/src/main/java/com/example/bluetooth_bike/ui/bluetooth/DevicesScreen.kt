package com.example.bluetooth_bike.ui.bluetooth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bluetooth_bike.domain.BluetoothDevice
import kotlinx.coroutines.launch

@Composable
fun DevicesScreen(
    navigationController: NavHostController,
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { DevicesTopAppBar(/*navigationController*/) },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = { FloatingActionButton(snackBarHostState, onStartScan, onStopScan) },
        content = { innerPadding ->
            DevicesContent(
                innerPadding,
                pairedDevices = state.pairedDevices,
                scannedDevices = state.scannedDevices,
                onClick = {}
            )
        }
    )
}

@Composable
fun DevicesContent(
    innerPadding: PaddingValues, pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit,
) {

    LazyColumn(
        Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices) { device ->
            //put the address next to the name in a Text
            Row (){
                Text(
                    text = device.name ?: "(No name)",
                    modifier = androidx.compose.ui.Modifier
                        .clickable { onClick(device) }
                        .padding(16.dp)
                )
                Text(
                    text = device.address,
                    modifier = Modifier
                        .clickable { onClick(device) }
                        .padding(16.dp)
                )
            }

        }
        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) { device ->
            Row {
                Text(
                    text = device.name ?: "(No name)",
                    modifier = Modifier
                        .clickable { onClick(device) }
                        .padding(16.dp)
                )
                Text(
                    text = device.address,
                    modifier = Modifier
                        .clickable { onClick(device) }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FloatingActionButton(
    snackBarHostState: SnackbarHostState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var clickCount by remember { mutableIntStateOf(0) }
    ExtendedFloatingActionButton(
        onClick = {
            onStartScan()

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
fun DevicesTopAppBar(/*navHostController: NavHostController*/) {
    TopAppBar(
        title = { Text("Connect device") },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO navHostController.navigate */ }
            ) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Go back")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO: settings */ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        },
    )
}
