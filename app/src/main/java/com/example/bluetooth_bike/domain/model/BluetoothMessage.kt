package com.example.bluetooth_bike.domain.model

data class BluetoothMessage(
    val voltage: String,
    val amperes: String,
    val speed: String,
    val trip : String,
    val total : String,
    val senderName: String,
    val isFromLocalUser: Boolean,
)