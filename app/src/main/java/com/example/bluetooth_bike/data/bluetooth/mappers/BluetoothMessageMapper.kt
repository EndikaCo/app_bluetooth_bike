package com.example.bluetooth_bike.data.bluetooth.mappers

import com.example.bluetooth_bike.domain.model.BtMessage

fun String.toBtMessage(): BtMessage {
    val parts = this.split("#", "$", "%", "&", "*")
    return BtMessage(
        voltage = parts.getOrNull(1) ?: "",
        amperes = parts.getOrNull(2) ?: "",
        speed = parts.getOrNull(3) ?: "",
        trip = parts.getOrNull(4) ?: "",
        total = parts.getOrNull(5) ?: "",
    )
}