package com.example.bluetooth_bike.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateAndTime{

    fun getCurrentDateTime() : LocalDateTime {
        return LocalDateTime.now()
    }

    fun formatDayOfWeek(datetime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("EEE")
        return datetime.format(formatter).uppercase(Locale.getDefault())
    }

    fun formatHour(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return dateTime.format(formatter)
    }

    fun formatMonth(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMM")
        return dateTime.format(formatter).uppercase(Locale.getDefault())
    }
}
