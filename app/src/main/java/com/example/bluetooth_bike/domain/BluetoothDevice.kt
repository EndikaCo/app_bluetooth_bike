package com.example.bluetooth_bike.domain

import android.bluetooth.BluetoothClass


typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String,
    val btType: Int,
    val btClass: BluetoothClass
)