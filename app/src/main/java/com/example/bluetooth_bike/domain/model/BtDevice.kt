package com.example.bluetooth_bike.domain.model

import android.bluetooth.BluetoothClass

data class BtDevice(
    val name: String?,
    val address: String,
    val btType: Int,
    val btClass: BluetoothClass?,
    val isPaired : Boolean
)