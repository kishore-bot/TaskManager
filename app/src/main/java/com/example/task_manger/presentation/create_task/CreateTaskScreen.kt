package com.example.task_manger.presentation.create_task

import android.app.Activity


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.domian.model.InputTask
import com.example.task_manger.presentation.create_task.components.PriorityRadioButtons
import com.example.task_manger.presentation.create_task.components.SubTaskInputDialog
import com.example.task_manger.presentation.create_task.components.SubtaskCard
import com.example.task_manger.presentation.utils.TaskTopBar
import java.util.Locale

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import com.example.task_manger.presentation.utils.DatePickerScreen
import com.example.task_manger.presentation.utils.TaskButton
import com.example.task_manger.presentation.utils.TaskTextField
import com.example.task_manger.presentation.utils.TimePickerScreen
import com.example.task_manger.presentation.utils.showToastMessage
import kotlinx.coroutines.delay


@Composable
fun CreateTaskScreen(
    viewModel: CreateTaskViewModel,
    navController: NavController,
) {
    val activity = LocalContext.current as? Activity
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var pr by remember {
        mutableStateOf("medium")
    }
    val subTasks = remember { mutableStateListOf<InputSubTask>() }
    var showDialog by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val result by viewModel.result.observeAsState(initial = "")

    LaunchedEffect(result) {
        if (result.isNotEmpty()) {
            if (activity != null) {
                showToastMessage(activity = activity, message = result)
            }
            delay(500)
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        TaskTopBar(onBackClick = { navController.popBackStack() }, title = "Create A Task")
        TaskTextField(textState = title, text = "Task Title", modifier = Modifier.padding(10.dp))
        TaskTextField(
            textState = description, text = "Task Description", modifier = Modifier.padding(10.dp)
        )
        Text(
            "Choose Priority ",
            fontSize = 20.sp,
            modifier = Modifier.padding(top=10.dp, start = 15.dp),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.height(5.dp))
        PriorityRadioButtons(onPrioritySelected = {
            pr = it.toString()
        })
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            DatePickerScreen(
                dateTaken = { it ->
                    date = it.toString()
                }, modifier = Modifier.width(190.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            TimePickerScreen(
                timeTaken = { it ->
                    time = it.toString()
                }, modifier = Modifier.width(200.dp)
            )
        }
        TaskButton(
            title = "Add SubTask", modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            showDialog = true
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                if (activity != null) {
                    handleTaskPosting(
                        title = title.value,
                        description = description.value,
                        time = time,
                        viewModel = viewModel,
                        date = date,
                        priority = pr,
                        subTasks = subTasks,
                        activity = activity
                    )
                }
            },
        ) {
            Text("Add Task", fontSize = 20.sp)
        }
        LazyColumn {
            items(subTasks.size) { index ->
                SubtaskCard(name = subTasks[index].name,
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        subTasks.removeAt(index) // Remove the subtask at the clicked index
                    })
            }
        }
        if (showDialog) {
            SubTaskInputDialog(onAddedSubTask = { task ->
                subTasks.add(task)
            }, onDismiss = { showDialog = false }, modifier = Modifier.height(400.dp))
        }
    }
}

private fun handleTaskPosting(
    title: String,
    description: String,
    time: String,
    date: String,
    activity: Activity,
    priority: String,
    subTasks: List<InputSubTask>,
    viewModel: CreateTaskViewModel,
) {
    if (description == "" || title == "" || time == "" || date == "") {

        showToastMessage("Select All Fields", activity = activity)
    } else {
        val inputTask = InputTask(
            description = description,
            subtasks = subTasks,
            title = title,
            priority = priority.lowercase(Locale.getDefault()),
            endTime = "${date}T${time}"
        )
        viewModel.onEvent(
            CreateTaskEvent.ChangeTask(
                task = inputTask
            )
        )
        viewModel.onEvent(CreateTaskEvent.PostTask)
    }
}
