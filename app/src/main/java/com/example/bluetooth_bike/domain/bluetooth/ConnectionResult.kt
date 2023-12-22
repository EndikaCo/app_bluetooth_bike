package com.example.bluetooth_bike.domain.bluetooth

import com.example.bluetooth_bike.domain.model.BluetoothMessage

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult

    data class Error(val message: String): ConnectionResult

    data class TransferSucceeded(val message: BluetoothMessage): ConnectionResult
}