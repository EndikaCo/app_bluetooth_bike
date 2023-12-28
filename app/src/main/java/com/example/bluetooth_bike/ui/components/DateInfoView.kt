package com.example.bluetooth_bike.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bluetooth_bike.domain.model.TimeModel
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme

@Composable
fun DateInfoView(time: TimeModel) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val (dayId, hourId, monthId) = createRefs()

        Text(
            text = time.day,
            modifier = Modifier.constrainAs(dayId) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(hourId.start)
            },
        )
        Text(
            text = time.hour,
            fontSize = 40.sp,
            modifier = Modifier.constrainAs(hourId) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = time.date,
            modifier = Modifier.constrainAs(monthId) {
                start.linkTo(hourId.end)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview
@Composable
fun PreviewDateInfoView() {
    Bluetooth_bikeTheme {
        DateInfoView(TimeModel("Mon", "12:00", "Jan"))
    }
}