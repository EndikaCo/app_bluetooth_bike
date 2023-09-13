package com.example.bluetooth_bike.ui.bluetooth

import com.example.bluetooth_bike.domain.BluetoothMessage
import com.example.bluetooth_bike.domain.BtDevice

data class BluetoothUiState(
    val scannedDevices: List<BtDevice> = emptyList(),
    val pairedDevices: List<BtDevice> = emptyList(),
    val isScanning : Boolean = false,
    val isConnected : Boolean = false,
    val isConnecting : Boolean = false,
    val errorMessage : String? = null,
    val messages: List<BluetoothMessage> = emptyList()
)
