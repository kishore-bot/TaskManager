package com.example.task_manger.presentation.home.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_manger.R
import com.example.task_manger.domian.model.Task
import com.example.task_manger.presentation.utils.ProgressSection

@Composable
fun TaskCard(
    task: Task,
    onClick: (Task) -> Unit,
) {
    var nextSubtask = task.title
    for (subtask in task.subtask ?: emptyList()) {
        if (subtask._id == task.nextSubtask) {
            nextSubtask = subtask.name.toString()
            break
        }
    }

    val time = if (task.daysLeft > 0) {
        "Days Left: ${task.daysLeft}"
    } else "Time Left: ${task.timeRemaining}"

    val color = when (task.priority) {
        "high" -> R.color.high
        "medium" -> R.color.medium
        else -> R.color.low  // Covers any other priority values
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(task) }
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = color),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(30.dp)
        ) {
            ProgressSection(
                task,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 20.dp),
                modifier1 = Modifier
                    .width(200.dp)
                    .padding(top = 3.dp)
            )
            Text(
                text = task.title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Upcoming Task: $nextSubtask", fontSize = 20.sp, fontWeight = FontWeight.Bold
            )

            Row {
                Text(
                    text = time, fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
