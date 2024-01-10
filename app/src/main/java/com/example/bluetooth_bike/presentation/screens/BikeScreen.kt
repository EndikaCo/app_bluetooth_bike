package com.example.bluetooth_bike.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetooth_bike.R
import com.example.bluetooth_bike.domain.model.BtMessage
import com.example.bluetooth_bike.domain.model.UiState
import com.example.bluetooth_bike.domain.model.TimeModel
import com.example.bluetooth_bike.presentation.components.BatteryInfoView
import com.example.bluetooth_bike.presentation.components.BatteryValuesChart
import com.example.bluetooth_bike.presentation.components.DateInfoView
import com.example.bluetooth_bike.presentation.theme.Bluetooth_bikeTheme

@Composable
fun BikeScreen(
    uiState: UiState,
    onDisconnect: () -> Unit,
    onStartClick: () -> Unit,
    onLightClick: () -> Unit
) {
    val name =
        if (uiState.connectedDevice != null && (uiState.connectedDevice.name?.isNotEmpty()) ==  true)
            uiState.connectedDevice.name
        else "Unknown"

    Scaffold(
        topBar = { MyTopBar(name, onDisconnect) {/*todo*/ } },
        bottomBar = { MyBottomBar(onStartClick, onLightClick) }
    ) { innerPadding ->
        BikeScreenContent(innerPadding, uiState)
    }
}

@Composable
fun BikeScreenContent(innerPadding: PaddingValues, uiState: UiState) {

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
        BatteryValuesChart(uiState.values)
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
fun TripKmView(trip: String, total: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = stringResource(R.string.trip_km), fontSize = 10.sp)
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
        Text(text = stringResource(R.string.total_km), fontSize = 10.sp)
    }
}

@Composable
fun SpeedInfoView(speed: String = "00") {
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
        Text(text = speed, fontSize = 120.sp, modifier = Modifier.padding(start = 50.dp))
        Text(
            text = stringResource(R.string.km_h),
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 25.dp)
        )
    }
}

@Composable
fun MyTopBar(name: String, onDisconnect: () -> Unit, onSettingsClick: () -> Unit) {
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
                contentDescription = stringResource(R.string.close),
            )
        }
        Box {
            Text(
                text = stringResource(R.string.connected_to, name),
                textAlign = TextAlign.Center,
            )
        }
        IconButton(
            onClick = { onSettingsClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings),
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

        IconButton(
            onClick = { onStartClick() },
            modifier = Modifier.size(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.start_icon),
                contentDescription = stringResource(R.string.start),
                modifier = Modifier.size(40.dp)
            )
        }
        IconButton(
            onClick = { onLightClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Column {
                Icon(
                    imageVector = Icons.Default.Highlight,
                    contentDescription = stringResource(R.string.turn_on_light),
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBikeScreen() {
    Bluetooth_bikeTheme {

        val values = listOf(
            BtMessage("65", "30", "30", "100", "1233"),
            BtMessage("66", "2", "25", "100", "1233"),
            BtMessage("59", "50", "00", "100", "1233"),
            BtMessage("67", "0", "10", "100", "1233"),
            BtMessage("66", "0", "00", "100", "1233"),
            BtMessage("68", "0", "00", "100", "1233"),
            BtMessage("63", "0", "00", "100", "1233"),
            BtMessage("65", "0", "00", "100", "1233"),
        )

        BikeScreen(
            UiState(
                time = TimeModel("Mon", "12:00", "12 Jan"),
                values = values
            ),
            {},
            {},
            {}
        )
    }
}