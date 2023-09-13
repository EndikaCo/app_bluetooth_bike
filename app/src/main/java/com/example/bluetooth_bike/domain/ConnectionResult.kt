package com.example.bluetooth_bike.domain

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult

    data class Error(val message: String): ConnectionResult

    data class TransferSucceeded(val message: BluetoothMessage): ConnectionResult
}