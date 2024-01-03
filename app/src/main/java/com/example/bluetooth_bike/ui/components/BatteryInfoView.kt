package com.example.bluetooth_bike.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetooth_bike.domain.model.BtMessage
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme

fun calculateBatteryPercentage(currentVoltage: Float, minVoltage: Float, maxVoltage: Float): Float {
    val percentage = (currentVoltage - minVoltage) / (maxVoltage - minVoltage)
    if (percentage < 0) return 0f
    return percentage
}

@Composable
fun BatteryInfoView(values: BtMessage) {

    val voltage = values.voltage
    val batteryPercentage: Float = calculateBatteryPercentage(voltage.toFloat(), 40f, 67.2f)
    val amperes = values.amperes.toFloat()
    val watts = values.amperes.toFloat() * values.voltage.toFloat()

    val batteryColor = when {
        batteryPercentage >= 0.6f -> Color.Green
        batteryPercentage > 0.3f -> Color.Yellow
        else -> Color.Red
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "${amperes}A", textAlign = TextAlign.End)
            Text(text = "${watts}W", textAlign = TextAlign.End)
        }
        LinearProgressIndicator(
            progress = batteryPercentage,
            color = batteryColor,
            modifier = Modifier
                .width(150.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp)) // Add this line
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
        )
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "${(batteryPercentage * 100).toInt() }%")
            Text(text = "${voltage}V")
        }
    }
}

@Preview
@Composable
fun PreviewBatteryInfoView() {
    val mockData =
        BtMessage(
            voltage = "50",
            amperes = "1",
            speed = "10",
            trip = "10",
            total = "10",
            senderName = "test")
    BatteryInfoView(mockData)
}


@Preview
@Composable
fun PreviewBatteryInfoView2() {
    val mockData =
        BtMessage(
            voltage = "65",
            amperes = "1.56",
            speed = "10",
            trip = "10.50",
            total = "101.50",
            senderName = "test")
    BatteryInfoView(mockData)
}

@Preview
@Composable
fun PreviewBatteryInfoView3() {
    Bluetooth_bikeTheme {

    val mockData =
        BtMessage(
            voltage = "48",
            amperes = "1.56",
            speed = "10",
            trip = "10.50",
            total = "101.50",
            senderName = "test")
    BatteryInfoView(mockData)}
}