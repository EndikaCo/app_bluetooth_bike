package com.example.bluetooth_bike.data.bluetooth.mappers

import com.example.bluetooth_bike.domain.model.BtMessage
import org.junit.Assert.*
import org.junit.Test

class BluetoothMessageTest {
    @Test
    fun `test toBluetoothMessage with valid input`() {
        val input = "#12.5$3.2%50&10*33"
        val expected = BtMessage(
            voltage = "12.5",
            amperes = "3.2",
            speed = "50",
            trip = "10",
            total = "33",
        )
        assertEquals(expected, input.toBtMessage())
    }

    @Test
    fun `test toBluetoothMessage with empty input`() {
        val input = ""
        val expected = BtMessage(
            voltage = "",
            amperes = "",
            speed = "",
            trip = "",
            total = "",
        )
        assertEquals(expected, input.toBtMessage())
    }

    @Test
    fun `test toBluetoothMessage with missing fields`() {
        val input = "#12.5$"
        val expected = BtMessage(
            voltage = "12.5",
            amperes = "",
            speed = "",
            trip = "",
            total = "",
        )
        assertEquals(expected, input.toBtMessage())
    }

    @Test
    fun `test toBluetoothMessage with extra fields`() {
        val input = "#12.5$3.2%50&10*extra"
        val expected = BtMessage(
            voltage = "12.5",
            amperes = "3.2",
            speed = "50",
            trip = "10",
            total = "extra",
        )
        assertEquals(expected, input.toBtMessage())
    }
}