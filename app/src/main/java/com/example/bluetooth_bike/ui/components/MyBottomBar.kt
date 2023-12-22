package com.example.bluetooth_bike.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetooth_bike.R
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme


@Composable
fun MyBottomBar(onStartClick: () -> Unit, onLightClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            IconButton(
                onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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

const val color = 0xFFFFFFFF

@Preview(name = "Light Mode", backgroundColor = color)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyBottomBar() {
    Bluetooth_bikeTheme {
        MyBottomBar(onStartClick, onLightClick)
    }
}
