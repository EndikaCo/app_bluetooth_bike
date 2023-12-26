package com.example.bluetooth_bike.data.bluetooth

import com.example.bluetooth_bike.domain.model.BluetoothMessage

fun String.toBluetoothMessage(isFromLocalUser: Boolean): BluetoothMessage {
    val name = substringBefore("#")
    val voltage = substringAfter("#").substringBefore("$")
    val amperes = substringAfter("$").substringBefore("%")
    val speed = substringAfter("%").substringBefore("&")
    val trip = substringAfter("&").substringBefore("*")
    val total = substringAfter("*")
    return BluetoothMessage(
        voltage = voltage,
        amperes = amperes,
        speed = speed,
        trip = trip,
        total = total,
        senderName = name,
        isFromLocalUser = isFromLocalUser
    )
}



fun BluetoothMessage.toByteArray(): ByteArray {
    return "$senderName#$voltage".encodeToByteArray()
}