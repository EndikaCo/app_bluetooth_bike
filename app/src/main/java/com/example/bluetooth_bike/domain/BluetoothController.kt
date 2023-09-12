package com.example.bluetooth_bike.domain

import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val isScanning: StateFlow<Boolean>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()
}