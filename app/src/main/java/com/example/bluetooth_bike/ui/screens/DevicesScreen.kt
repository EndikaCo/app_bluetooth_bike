package com.example.bluetooth_bike.ui.screens

import android.bluetooth.BluetoothDevice
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetooth_bike.R
import com.example.bluetooth_bike.domain.model.BtDevice
import com.example.bluetooth_bike.domain.model.UiState
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme
import kotlinx.coroutines.launch

@Composable
fun DevicesScreen(
    state: UiState,
    onStartServer: () -> Unit,
    onDeviceClick: (BtDevice) -> Unit,
    onScanClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = state.isConnected) {
        if (state.isConnected) {
            Toast.makeText(
                context,
                "Connected!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { DevicesTopAppBar(onStartServer, onCloseClick) },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = { FloatingActionButton(snackBarHostState, onScanClick, state) },
        content = { innerPadding ->
            DevicesContent(innerPadding, state = state, onClick = { onDeviceClick(it) })
        }
    )
}

@Composable
fun DevicesContent(
    innerPadding: PaddingValues, state: UiState,
    onClick: (BtDevice) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(innerPadding))
        DevicesList(
            title = "Paired devices",
            devices = state.pairedDevices,
            onClick = onClick
        )
        DevicesList(
            title = "Scanned devices",
            devices = state.scannedDevices,
            onClick = onClick
        )
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun DevicesList(
    title: String,
    devices: List<BtDevice>,
    onClick: (BtDevice) -> Unit
) {
    if (devices.isEmpty()) return
    Text(
        title,
        textAlign = TextAlign.Center,
        fontSize = 15.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, top = 10.dp)
    )
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
    ) {
        items(devices) { device ->
            DeviceItem(device, onClick)
        }
    }
}

@Composable
fun DeviceItem(device: BtDevice, onClick: (BtDevice) -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .clickable { onClick(device) }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                .height(50.dp)
        ) {
            DeviceImage(device)
            Column(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = device.name ?: "Unknown",
                    modifier = Modifier
                        .height(25.dp)
                )
                Text(
                    text = device.address,
                    modifier = Modifier
                        .height(25.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.CenterEnd, // Center content in the Box

            ) {
                if (device.btType == BluetoothDevice.DEVICE_TYPE_LE)
                    Text(text = "BLE", color = Color(0xFF249696), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun DeviceImage(btDevice: BtDevice) {
    val imageResource =
        if (btDevice.name?.contains("bike") == true)
            R.drawable.ebike_24
        else R.drawable.bluetooth_24

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.Gray, CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "logo",
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
        )
    }
}

@Composable
fun FloatingActionButton(
    snackBarHostState: SnackbarHostState,
    onScanClick: () -> Unit,
    state: UiState
) {
    val scope = rememberCoroutineScope()

    ExtendedFloatingActionButton(
        onClick = {
            onScanClick()
            scope.launch {
                if (state.isScanning)
                    snackBarHostState.showSnackbar("Scanning bluetooth devices")
            }
        }, modifier = Modifier.height(50.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            if (state.isScanning) {
                CircularProgressIndicator(Modifier.size(20.dp))
                Text("Scanning", Modifier.padding(start = 10.dp, end = 10.dp))
            } else
                Text("Scan new devices", Modifier.padding(start = 10.dp, end = 10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesTopAppBar(
    onStartServer: () -> Unit,
    onCloseClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                "Bluetooth devices",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                fontFamily = FontFamily.SansSerif,
            )
        },
        navigationIcon = {
            IconButton(onClick = {onCloseClick() }) {
                Icon(Icons.Filled.Close, contentDescription = "Close App")
            }
        },
        actions = {
            Text(
                text = "start\nserver",
                modifier = Modifier
                    .clickable { onStartServer() }
                    .padding(end = 10.dp),
                color = Color(0xFF7A7A7A),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp
            )
        },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDevicesScreen() {
    Bluetooth_bikeTheme {

        // Create a BtDevice
        val exampleDevice = BtDevice(
            name = "Test Device",
            address = "00:11:22:33:44:55",
            btType = BluetoothDevice.DEVICE_TYPE_CLASSIC,
            btClass = null
        )

        val exampleBikeDevice = BtDevice(
            name = "Test E bike",
            address = "00:11:22:33:44:55",
            btType = BluetoothDevice.DEVICE_TYPE_CLASSIC,
            btClass = null
        )

        DevicesScreen(
            state = UiState(
                scannedDevices = listOf(
                    exampleDevice,
                    exampleDevice,
                    exampleDevice,
                    exampleBikeDevice
                ),
                pairedDevices = listOf(
                    exampleDevice,
                    exampleDevice,
                    exampleDevice,
                    exampleDevice,
                )
            ),
            onStartServer = {},
            onDeviceClick = {},
            onScanClick = {},
            onCloseClick = {}
        )
    }
}
