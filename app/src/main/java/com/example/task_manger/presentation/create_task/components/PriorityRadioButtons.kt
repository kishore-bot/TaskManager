package com.example.task_manger.presentation.create_task.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_manger.domian.model.TaskPriority

@Composable
fun PriorityRadioButtons(
    onPrioritySelected: (TaskPriority) -> Unit,
) {
    val options = listOf("Low", "Medium", "High")
    var selectedOption by remember { mutableStateOf(TaskPriority.MEDIUM) }

    Row(modifier = Modifier.fillMaxWidth()) {
        options.forEach { option ->
            val priorityEnum = TaskPriority.valueOf(option.uppercase())
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(selected = selectedOption == priorityEnum, onClick = {
                        selectedOption = priorityEnum
                        onPrioritySelected(priorityEnum)
                    })
                    .padding(horizontal = 8.dp)) {
                RadioButton(modifier = Modifier.size(45.dp),
                    selected = selectedOption == priorityEnum,
                    onClick = {
                        selectedOption = priorityEnum
                        onPrioritySelected(priorityEnum)
                    })
                Text(
                    text = option,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.labelLarge.merge(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

