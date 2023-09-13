package com.example.bluetooth_bike.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val scannedDevices: StateFlow<List<BtDevice>>
    val pairedDevices: StateFlow<List<BtDevice>>
    val isScanning: StateFlow<Boolean>
    val isConnected: StateFlow<Boolean>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()

    fun startBluetoothServer(): Flow<ConnectionResult>
    fun connectToDevice(device: BtDevice): Flow<ConnectionResult>

    fun closeConnection()
    fun release()
}