package com.example.task_manger.presentation.add_sub_task

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.presentation.utils.DatePickerScreen
import com.example.task_manger.presentation.utils.TaskButton
import com.example.task_manger.presentation.utils.TaskTextField
import com.example.task_manger.presentation.utils.TaskTopBar
import com.example.task_manger.presentation.utils.TimePickerScreen
import com.example.task_manger.presentation.utils.showToastMessage
import kotlinx.coroutines.delay

@Composable
fun AddSubTaskScreen(
    viewModel: AddSubTaskViewModel,
    taskId: String,
    onBackClick: () -> Unit,
) {
    val activity = LocalContext.current as? Activity

    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val result by viewModel.completedRes.observeAsState(initial = "")

    LaunchedEffect(result) {
        if (result != "") {
            if (result != "Error") {
                if (activity != null) {
                    showToastMessage(message = result, activity = activity)
                }
                delay(200)
                onBackClick()
            } else {
                if (activity != null) {
                    showToastMessage(message = result, activity = activity)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskTopBar(title = "Add New Sub Task", onBackClick = { onBackClick() })
        TaskTextField(
            textState = name, text = "Task Name", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        TaskTextField(
            textState = description,
            text = "Task Description",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DatePickerScreen(dateTaken = {
                date = it.toString()
            }, modifier = Modifier.width(200.dp))
            Spacer(modifier = Modifier.width(10.dp))
            TimePickerScreen(timeTaken = {
                time = it.toString()
            }, modifier = Modifier.width(200.dp))
        }
        TaskButton(
            title = "Done", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            if (activity != null) {
                addSubTask(
                    taskId = taskId,
                    name = name.value,
                    description = description.value,
                    time = time,
                    date = date,
                    activity = activity,
                    viewModel = viewModel,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun addSubTask(
    taskId: String,
    name: String,
    description: String,
    time: String,
    date: String,
    activity: Activity,
    viewModel: AddSubTaskViewModel,
) {
    if (time == "" || name == "" || description == "" || date == "") {
        showToastMessage(message = "Select All Field", activity)
    } else {
        val subTask = InputSubTask(
            name = name,
            description = description,
            endTime = "${date}T${time}",
        )
        viewModel.onEvent(AddSubTaskEvent.AddTaskId(taskId = taskId))
        viewModel.onEvent(AddSubTaskEvent.AddSubTask(subTask = subTask))
        viewModel.onEvent(AddSubTaskEvent.PostSubTask)
    }
}
