package com.example.task_manger.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.task_manger.domian.model.Task
import com.example.task_manger.presentation.utils.ErrorScreen

@Composable
fun TaskList(
    tasksList: State<List<Task>>?,
    message: String,
    onClick: (Task) -> Unit
) {
    if (tasksList != null && tasksList.value.isNotEmpty()) {
        LazyColumn {
            items(tasksList.value.size) { index ->
                val task = tasksList.value[index]
                TaskCard(task = task, onClick)
            }
            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    } else {
        ErrorScreen(message = message)
    }
}
