package com.example.bluetooth_bike.domain.model

data class UiState(
    val pairedDevices: List<BtDevice> = emptyList(),
    val isScanning : Boolean = false,
    val isConnected : Boolean = false,
    val isConnecting : Boolean = false,
    val errorMessage : String? = null,
    val values: List<BtMessage> = listOf(

        BtMessage(
            voltage = "0.0",
            amperes = "0.0",
            speed = "00",
            trip = "0.0",
            total = "0.0",
            senderName = "-",
            isFromLocalUser = false
        )
    ),
    val time : TimeModel = TimeModel(),
)
