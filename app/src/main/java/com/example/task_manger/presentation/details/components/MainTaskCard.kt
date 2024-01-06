package com.example.task_manger.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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


@Composable
fun MainTaskCard(
    task: Task,
    modifier: Modifier = Modifier,
) {
    val created = task.createdAt.let {
        "${it.substringBefore("T")} ${it.substringAfter("T").substringBefore(".")}"
    }
    val end = task.endTime.let {
        "${it.substringBefore("T")} ${it.substringAfter("T").substringBefore(".")}"
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.card)),
    ) {
        Column(
            modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.Start
        ) {
            Text(text = task.title, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = task.description, fontSize = 20.sp, fontWeight = FontWeight.W500)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Priority: ${task.priority.uppercase()}",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = "Created At: $created", fontSize = 20.sp, fontWeight = FontWeight.W500
            )
            Text(
                text = "End Date: $end", fontSize = 20.sp, fontWeight = FontWeight.W500
            )
            if (task.completed) {
                val comp = task.completedDate.let {
                    "${it.substringBefore("T")} ${
                        it.substringAfter("T").substringBefore(".")
                    }"
                }
                Text(
                    text = "Completed At: $comp", fontSize = 20.sp, fontWeight = FontWeight.W500
                )
            } else {

                Text(
                    text = "Days Left: ${task.daysLeft}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
                Text(
                    text = "Time Left: ${task.timeRemaining}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}

