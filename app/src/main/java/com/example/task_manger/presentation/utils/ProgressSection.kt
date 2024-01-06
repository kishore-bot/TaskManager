package com.example.task_manger.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_manger.R
import com.example.task_manger.domian.model.Task

@Composable
fun ProgressSection(
    task: Task,
    modifier: Modifier = Modifier,
    modifier1: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
    ) {
        Text(
            text = task.completedSubtasksCount.toString(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgressBar(
            totalTasks = task.totalSubtasksCount,
            tasksCompleted = task.completedSubtasksCount,
            modifier = modifier1
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = task.totalSubtasksCount.toString(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun LinearProgressBar(
    totalTasks: Int,
    tasksCompleted: Int,
    modifier: Modifier = Modifier,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = colorResource(id = R.color.backGroundLight),
) {
    val progress = tasksCompleted.toFloat() / totalTasks.toFloat()
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .height(20.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .background(backgroundColor)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth(progress)
                .height(20.dp)
                .background(progressColor)

        )
    }
}