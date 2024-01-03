package com.example.bluetooth_bike.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetooth_bike.R
import com.example.bluetooth_bike.domain.model.BtMessage
import com.example.bluetooth_bike.domain.model.Point
import com.example.bluetooth_bike.domain.model.TimeModel
import com.example.bluetooth_bike.domain.model.UiState
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme


fun parseBtMessagesToPoints(btMessages: List<BtMessage>): List<Point> {
    return btMessages.mapIndexed { index, btMessage ->
        Point(index.toFloat(), btMessage.voltage.toFloat())
    }
}

@Composable
fun BatteryValuesChart(values: List<BtMessage>) {

    val parsedValues = parseBtMessagesToPoints(values)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background (MaterialTheme.colorScheme.background)
    ) {
        LineChart(Modifier.size(250.dp, 200.dp), parsedValues)
        Spacer(modifier = Modifier.height(10.dp))
        ShowLegend()
    }
}

@Composable
fun LineChart(modifier: Modifier, voltage: List<Point>) {

    // find max and min value of X, we will need that later
    val minXValue = voltage.minOf { it.x }
    val maxXValue = voltage.maxOf { it.x }

    val minYValue = 45f
    val maxYValue = 80f

    // create Box with canvas
    Box(modifier = modifier
        .background(MaterialTheme.colorScheme.background)
        .drawBehind { // we use drawBehind() method to create canvas

            // map data points to pixel values, in canvas we think in pixels
            val pixelPoints = voltage.map {
                // we use extension function to convert and scale initial values to pixels
                val x = it.x.mapValueToDifferentRange(
                    inMin = minXValue,
                    inMax = maxXValue,
                    outMin = 0f,
                    outMax = size.width
                )

                // same with y axis
                val y = it.y.mapValueToDifferentRange(
                    inMin = minYValue,
                    inMax = maxYValue,
                    outMin = size.height,
                    outMax = 0f
                )

                Point(x, y)
            }

            val path = Path() // prepare path to draw

            // in the loop below we fill our path
            pixelPoints.forEachIndexed { index, point ->
                if (index == 0) { // for the first point we just move drawing cursor to the position
                    path.moveTo(point.x, point.y)
                } else {
                    path.lineTo(point.x, point.y) // for rest of points we draw the line
                }
            }

            // and finally we draw the path
            drawPath(
                path,
                color = Color.Green,
                style = Stroke(width = 5f)
            )
        })
}

@Composable
fun ShowLegend() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Green, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = stringResource(R.string.voltage), fontSize = 6.sp, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Yellow, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = stringResource(R.string.amperes), fontSize = 6.sp, style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Blue, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = stringResource(R.string.speed), fontSize = 6.sp, style = MaterialTheme.typography.titleSmall)
    }
}

// simple extension function that allows conversion between ranges
fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin


@Preview
@Composable
fun PreviewBatteryValuesChart() {

    val values = listOf(
        BtMessage("65", "30", "30", "100", "1233", "Ebike01"),
        BtMessage("66", "2", "25", "100", "1233", "Ebike01"),
        BtMessage("59", "50", "00", "100", "1233", "Ebike01"),
        BtMessage("67", "0", "10", "100", "1233", "Ebike01"),
        BtMessage("66", "0", "00", "100", "1233", "Ebike01"),
        BtMessage("68", "0", "00", "100", "1233", "Ebike01"),
        BtMessage("63", "0", "00", "100", "1233", "Ebike01"),
        BtMessage("65", "0", "00", "100", "1233", "Ebike01"),
    )
    
    val uiState = UiState(
        time = TimeModel("Mon", "12:00", "12 Jan"), values = values
    )

    Bluetooth_bikeTheme {
        BatteryValuesChart(uiState.values)
    }
}