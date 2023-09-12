package com.example.bluetooth_bike.ui.bluetooth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bluetooth_bike.R
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
        floatingActionButton = {
            FloatingActionButton(
                snackBarHostState,
                onStartScan,
                onStopScan,
                state
            )
        },
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

    Column(Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.padding(innerPadding))

        DevicesContainer(
            "Paired devices",
            Modifier
                .padding(16.dp)
                .weight(0.5F)
                .clip(RoundedCornerShape(16.dp)) // Add rounded corners
            , pairedDevices, onClick
        )
        DevicesContainer(
            "Scanned devices",
            Modifier
                .padding(16.dp)
                .weight(1F)
                .clip(RoundedCornerShape(16.dp)) // Add rounded corners
            , scannedDevices, onClick
        )

        Spacer(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun DevicesContainer(
    title: String,
    modifier: Modifier,
    pairedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit
) {
    LazyColumn(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Title(title)
        }
        items(pairedDevices) { device ->
            DeviceItem(device, onClick)
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun DeviceItem(device: BluetoothDevice, onClick: (BluetoothDevice) -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp) // Size of the Box (background)
                    .background(Color.Gray, CircleShape) // Round background
                    .align(Alignment.CenterVertically), // Center vertically in the Row
                contentAlignment = Alignment.Center // Center content in the Box
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ebike_24),
                    contentDescription = "logo",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            Column(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = device.name ?: "Unknown",
                    modifier = Modifier
                        .height(25.dp)
                        .clickable { onClick(device) }
                )
                Text(
                    text = device.address,
                    modifier = Modifier
                        .height(25.dp)
                        .clickable { onClick(device) }
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically), // Size of the Box (background)
                contentAlignment = Alignment.CenterEnd, // Center content in the Box

            ) {
                if (device.btType == 2)
                    Text(text = "BLE", color = Color(0xFF249696), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun FloatingActionButton(
    snackBarHostState: SnackbarHostState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    state: BluetoothUiState
) {

    val scope = rememberCoroutineScope()

    ExtendedFloatingActionButton(
        onClick = {
            if (state.isScanning)
                onStopScan()
            else
                onStartScan()
            // show snack-bar as a suspend function
            scope.launch {
                if (!state.isScanning)
                    snackBarHostState.showSnackbar("Scanning bluetooth devices")
            }
        }
    ) {
        val stateStr: String = if (state.isScanning)
            "Stop scanning"
        else
            "Scan"
        Text(stateStr)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesTopAppBar(/*navHostController: NavHostController*/) {
    TopAppBar(
        title = {
            Text(
                "Bluetooth devices",
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
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
