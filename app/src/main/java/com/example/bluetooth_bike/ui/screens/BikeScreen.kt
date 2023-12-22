@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.bluetooth_bike.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bluetooth_bike.domain.model.BluetoothUiState
import com.example.bluetooth_bike.domain.model.Point
import com.example.bluetooth_bike.ui.components.MyBottomBar
import com.example.bluetooth_bike.ui.components.MyTopBar
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun BikeScreen(
    uiState: BluetoothUiState,
    onDisconnect: () -> Unit,
    onStartClick: () -> Unit,
    onLightClick: () -> Unit
) {
    Scaffold(
        topBar = { MyTopBar(uiState, onDisconnect) },
        bottomBar = { MyBottomBar(onStartClick, onLightClick) }
    ) {
        BikeScreenContent(it)
    }
}

@Composable
fun BikeScreenContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        DateInfoView()
        Divider(Modifier.padding(top = 10.dp, bottom = 20.dp))
        BatteryInfoView(0.3f)
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        SpeedInfoView()
        TripKmView("12","1234")
        Divider(Modifier.padding(top = 10.dp, bottom = 20.dp))
        BatteryChart()
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
fun TripKmView(trip : String, total : String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "trip(km) ", fontSize = 10.sp)
        Text(
            text = trip,
            modifier = Modifier.padding(end = 20.dp),
            style = MaterialTheme.typography.titleSmall
        )

        Text(text = "of", style = MaterialTheme.typography.titleSmall)


        Text(
            text = total,
            modifier = Modifier.padding(start = 20.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = " total(km)", fontSize = 10.sp)
    }
}

@Composable
fun BatteryChart() {

    val values = listOf(
        Point(0f, 1f),
        Point(1.5f, 1.2f),
        Point(2f, 0.9f),
        Point(2.5f, 2f),
        Point(3f, 1.3f),
        Point(3.5f, 3.2f),
        Point(4f, 0.8f),
    )
    SuperSimpleLineChart(Modifier.size(250.dp, 200.dp), values)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Green, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "Voltage", fontSize = 6.sp, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Yellow, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "Amperes", fontSize = 6.sp, style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Blue, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "Speed", fontSize = 6.sp, style = MaterialTheme.typography.titleSmall)
    }
}


@Composable
fun SuperSimpleLineChart(modifier: Modifier, voltage: List<Point>) {

    // find max and min value of X, we will need that later
    val minXValue = voltage.minOf { it.x }
    val maxXValue = voltage.maxOf { it.x }

    // find max and min value of Y, we will need that later
    val minYValue = voltage.minOf { it.y }
    val maxYValue = voltage.maxOf { it.y }

    // create Box with canvas
    Box(modifier = modifier
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

// simple extension function that allows conversion between ranges
fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

@Composable
fun BatteryInfoView(batteryPercentage: Float) {
    val batteryColor = when {
        batteryPercentage >= 0.6f -> Color.Green
        batteryPercentage > 0.3f -> Color.Yellow
        else -> Color.Red
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "20.30A", textAlign = TextAlign.End)
            Text(text = "150.65W", textAlign = TextAlign.End)
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
            Text(text = "100%")
            Text(text = "50.65V")
        }
    }

}

@Composable
fun DateInfoView() {
    val currentDateTime = remember { mutableStateOf(getCurrentDateTime()) }

    LaunchedEffect(key1 = currentDateTime) {
        while (true) {
            delay(60000) // delay for 1 minute
            currentDateTime.value = getCurrentDateTime()
        }
    }

    val dateTime = currentDateTime.value
    val dayOfWeek = formatDayOfWeek(dateTime)
    val dayOfMonth = dateTime.dayOfMonth.toString()
    val month = formatMonth(dateTime)
    val hour = formatHour(dateTime)

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

        val (dayId, hourId, monthId) = createRefs()

        Text(
            text = dayOfWeek,
            modifier = Modifier.constrainAs(dayId) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(hourId.start)
            },
        )
        Text(
            text = hour,
            fontSize = 40.sp,
            modifier = Modifier.constrainAs(hourId) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = "$month $dayOfMonth",
            modifier = Modifier.constrainAs(monthId) {
                start.linkTo(hourId.end)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

fun getCurrentDateTime(): LocalDateTime {
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

@Composable
fun SpeedInfoView() {
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
        Text(text = "56", fontSize = 120.sp, modifier = Modifier.padding(start = 50.dp))
        Text(
            text = "km/h",
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 25.dp)
        )
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BikeScreenPreview() {
    Bluetooth_bikeTheme {
        BikeScreen(
            BluetoothUiState(),
            {},
            {},
            {}
        )
    }
}