package com.example.task_manger.presentation.create_task.components

import android.app.Activity
import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.unit.dp
import com.example.task_manger.R
import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.presentation.utils.DatePickerScreen
import com.example.task_manger.presentation.utils.TaskTextField
import com.example.task_manger.presentation.utils.TimePickerScreen


@Composable
fun SubTaskInputDialog(
    onAddedSubTask: (inputSubTask: InputSubTask) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = LocalContext.current as? Activity
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    AlertDialog(modifier = modifier,
        containerColor = colorResource(id = R.color.backGroundLight),
        onDismissRequest = onDismiss,
        title = { Text(text = "Add SubTask") },
        confirmButton = {
            Button(onClick = {
                if (time.isEmpty() || name.value.isEmpty() || description.value.isEmpty()) {
                    activity?.showToast("Select All Fields")
                } else {
                    val inputSubTask = InputSubTask(
                        description = description.value,
                        name = name.value,
                        endTime = "${date}T${time}"
                    )
                    onAddedSubTask(inputSubTask)
                    name.value = ""
                    description.value = ""
                    onDismiss()
                }

            }) {
                Text("Done")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                TaskTextField(textState = name, text = "SubTask Name")
                Spacer(modifier = Modifier.height(8.dp))
                TaskTextField(textState = description, text = "Subtask Description")
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DatePickerScreen(
                        dateTaken = { date = it.toString() }, modifier = Modifier.width(130.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TimePickerScreen(
                        timeTaken = { time = it.toString() }, modifier = Modifier.width(130.dp)
                    )
                }
            }
        })
}

fun Activity.showToast(message: String) {
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(this, message, duration)
    toast.show()
}
