package com.example.bluetooth_bike.domain.bluetooth

import com.example.bluetooth_bike.domain.model.BtDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val connectedDeviceName: StateFlow<String>
    val devices: StateFlow<List<BtDevice>>
    val isScanning: StateFlow<Boolean>
    val isConnected: StateFlow<Boolean>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()
    fun connectToDevice(device: BtDevice): Flow<ConnectionResult>
    suspend fun trySendMessage(message: String): String?
    fun closeConnection()
    fun release()
}