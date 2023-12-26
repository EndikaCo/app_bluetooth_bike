package com.example.bluetooth_bike.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetooth_bike.domain.model.BluetoothUiState
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme

@Composable
fun MyTopBar(uiState: BluetoothUiState, onDisconnect: () -> Unit, onSettingsClick: () -> Unit) {
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
            val senderName = "Aurelio's eBike"
            Text(
                text = "Connected to $senderName",
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


@Preview(name = "Light Mode", backgroundColor = 0xFFFFFFFF)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyTopBar() {
    Bluetooth_bikeTheme {
        MyTopBar(BluetoothUiState(), {}, {})
    }
}