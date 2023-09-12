package com.example.bluetooth_bike.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.bluetooth_bike.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address,
        btType = type,
        btClass = bluetoothClass
    )
}