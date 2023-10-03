package com.example.bluetooth_bike.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.bluetooth_bike.data.model.BtDevice

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BtDevice {
    return BtDevice(
        name = name,
        address = address,
        btType = type,
        btClass = bluetoothClass
    )
}