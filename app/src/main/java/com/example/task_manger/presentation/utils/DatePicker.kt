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
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(
    dateTaken: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    var date by remember {
        mutableStateOf("")
    }

    val calendarState = rememberSheetState()
    CalendarDialog(state = calendarState, selection = CalendarSelection.Date {
        date = it.toString()
        dateTaken(date)
    })
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TaskButton(modifier = modifier, title = "Date") {
            calendarState.show()
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = date,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colorResource(id = R.color.text)
        )
    }
}
