package com.example.bluetooth_bike.data.bluetooth.mappers

import com.example.bluetooth_bike.domain.model.BtMessage
import org.junit.Assert.*
import org.junit.Test

class BluetoothMessageTest {
    @Test
    fun `test toBluetoothMessage with valid input`() {
        val input = "John#12.5$3.2%50&10*33"
        val expected = BtMessage(
            voltage = "12.5",
            amperes = "3.2",
            speed = "50",
            trip = "10",
            total = "33",
            senderName = "John"
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
            senderName = ""
        )
        assertEquals(expected, input.toBtMessage())
    }

    @Test
    fun `test toBluetoothMessage with missing fields`() {
        val input = "Jane#12.5$"
        val expected = BtMessage(
            voltage = "12.5",
            amperes = "",
            speed = "",
            trip = "",
            total = "",
            senderName = "Jane"
        )
        assertEquals(expected, input.toBtMessage())
    }

    @Test
    fun `test toBluetoothMessage with extra fields`() {
        val input = "Mike#12.5$3.2%50&10*extra"
        val expected = BtMessage(
            voltage = "12.5",
            amperes = "3.2",
            speed = "50",
            trip = "10",
            total = "extra",
            senderName = "Mike"
        )
        assertEquals(expected, input.toBtMessage())
    }
}