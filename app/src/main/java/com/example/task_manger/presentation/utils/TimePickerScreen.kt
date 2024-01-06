package com.example.task_manger.presentation.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_manger.R
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerScreen(
    timeTaken: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    var time by remember {
        mutableStateOf("")
    }
    val clockState = rememberSheetState()
    ClockDialog(state = clockState, config = ClockConfig(
        is24HourFormat = false
    ), selection = ClockSelection.HoursMinutesSeconds { hours, minutes, seconds ->
        val paddedHours = hours.toString().padStart(2, '0')
        val paddedMinutes = minutes.toString().padStart(2, '0')
        val paddedSeconds = seconds.toString().padStart(2, '0')
        time = "$paddedHours:$paddedMinutes:${paddedSeconds}"
        timeTaken(time)
    })
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskButton(modifier = modifier, title = "Time") {
            clockState.show();
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = time,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colorResource(id = R.color.text)
        )
    }
}