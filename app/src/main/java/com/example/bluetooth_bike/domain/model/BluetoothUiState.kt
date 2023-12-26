package com.example.bluetooth_bike.domain.model

data class BluetoothUiState(
    val scannedDevices: List<BtDevice> = emptyList(),
    val pairedDevices: List<BtDevice> = emptyList(),
    val isScanning : Boolean = false,
    val isConnected : Boolean = false,
    val isConnecting : Boolean = false,
    val errorMessage : String? = null,
    val values: List<BluetoothMessage> = emptyList(),
    val time : TimeModel = TimeModel()
) {

}