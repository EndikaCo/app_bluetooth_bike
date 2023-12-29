
package com.example.bluetooth_bike.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetooth_bike.R
import com.example.bluetooth_bike.domain.model.BtMessage
import com.example.bluetooth_bike.domain.model.UiState
import com.example.bluetooth_bike.domain.model.TimeModel
import com.example.bluetooth_bike.ui.components.BatteryInfoView
import com.example.bluetooth_bike.ui.components.BatteryValuesChart
import com.example.bluetooth_bike.ui.components.DateInfoView
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme

@Composable
fun BikeScreen(
    uiState: UiState,
    onDisconnect: () -> Unit,
    onStartClick: () -> Unit,
    onLightClick: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.isConnected) {
        if (uiState.isConnected) {
            Toast.makeText(
                context,
                "Connected!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = { MyTopBar(uiState, onDisconnect, onSettingsClick = {/*todo*/ }) },
        bottomBar = { MyBottomBar(onStartClick, onLightClick) }
    ) { innerPadding ->
        BikeScreenContent(innerPadding, uiState)
    }
}

@Composable
fun BikeScreenContent(innerPadding: PaddingValues, uiState: UiState) {

    if (uiState.values.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        DateInfoView(uiState.time)
        Divider(Modifier.padding(top = 10.dp, bottom = 20.dp))
        BatteryInfoView(uiState.values.last())
        SpeedInfoView()
        TripKmView(uiState.values.last().trip, uiState.values.last().total)
        Divider(Modifier.padding(top = 10.dp, bottom = 20.dp))
        BatteryValuesChart()
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
        Text(text = "~", style = MaterialTheme.typography.titleSmall)
        Text(
            text = total,
            modifier = Modifier.padding(start = 20.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = " total(km)", fontSize = 10.sp)
    }
}

@Composable
fun SpeedInfoView(speed : String = "00") {
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
        Text(text = speed, fontSize = 120.sp, modifier = Modifier.padding(start = 50.dp))
        Text(
            text = "km/h",
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 25.dp)
        )
    }
}

@Composable
fun MyTopBar(uiState: UiState, onDisconnect: () -> Unit, onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onDisconnect() },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Close",
            )
        }
        Box {
            Text(
                text = if(uiState.values.isNotEmpty())"Connected to $uiState.values[0].senderName" else "Connecting...",
                textAlign = TextAlign.Center,
            )
        }
        IconButton(
            onClick = { onSettingsClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
            )
        }
    }
}

@Composable
fun MyBottomBar(onStartClick: () -> Unit, onLightClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(
                onClick = { onStartClick() },
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.start_icon),
                    contentDescription = "Start",
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(text = "Start", fontSize = 8.sp, style = MaterialTheme.typography.titleSmall)
        }
        IconButton(
            onClick = { onLightClick()},
            modifier = Modifier.size(24.dp)
        ) {
            Column {
                Icon(
                    imageVector = Icons.Default.Highlight,
                    contentDescription = "Turn ON Light",
                )
                Text(text = "Turn ON")
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBikeScreen() {
    Bluetooth_bikeTheme {
        BikeScreen(
            UiState(time = TimeModel("Mon", "12:00", "12 Jan"), values = listOf(
                BtMessage("60", "0", "00", "100", "1233", "Ebike01", false),
            )),
            {},
            {},
            {}
        )
    }
}