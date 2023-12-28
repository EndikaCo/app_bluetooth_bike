package com.example.bluetooth_bike.domain.bluetooth

import com.example.bluetooth_bike.domain.model.BtMessage

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
    data class TransferSucceeded(val message: BtMessage): ConnectionResult
}