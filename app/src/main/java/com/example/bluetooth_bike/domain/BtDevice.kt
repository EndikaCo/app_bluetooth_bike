package com.example.bluetooth_bike.domain

import android.bluetooth.BluetoothClass


typealias BluetoothDeviceDomain = BtDevice

data class BtDevice(
    val name: String?,
    val address: String,
    val btType: Int,
    val btClass: BluetoothClass
)