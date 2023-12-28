package com.example.bluetooth_bike.data.bluetooth.mappers

import com.example.bluetooth_bike.domain.model.BtMessage

fun String.toBluetoothMessage(isFromLocalUser: Boolean): BtMessage {
    val name = substringBefore("#")
    val voltage = substringAfter("#").substringBefore("$")
    val amperes = substringAfter("$").substringBefore("%")
    val speed = substringAfter("%").substringBefore("&")
    val trip = substringAfter("&").substringBefore("*")
    val total = substringAfter("*")
    return BtMessage(
        voltage = voltage,
        amperes = amperes,
        speed = speed,
        trip = trip,
        total = total,
        senderName = name,
        isFromLocalUser = isFromLocalUser
    )
}

fun BtMessage.toByteArray(): ByteArray {
    return "$senderName#$voltage".encodeToByteArray()
}