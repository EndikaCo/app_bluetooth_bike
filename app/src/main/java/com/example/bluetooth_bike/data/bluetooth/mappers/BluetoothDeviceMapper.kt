package com.example.bluetooth_bike.data.bluetooth.mappers

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.bluetooth_bike.domain.model.BtDevice

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBtDeviceDomain(isPaired : Boolean): BtDevice {
    return BtDevice(
        name = name,
        address = address,
        btType = type,
        btClass = bluetoothClass,
        isPaired = isPaired
    )
}