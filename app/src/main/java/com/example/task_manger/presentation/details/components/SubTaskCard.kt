package com.example.task_manger.presentation.details.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.task_manger.domian.model.Subtask


@Composable
fun SubTaskCard(
    task: Subtask,
    onDeleteSubTas: () -> Unit,
) {

    val created = task.startTime?.let {
        "${it.substringBefore("T")} ${it.substringAfter("T").substringBefore(".")}"
    }
    val end = task.endTime?.let {
        "${it.substringBefore("T")} ${it.substringAfter("T").substringBefore(".")}"
    }
    val color = if (task.completed == true) {
        R.color.completed
    } else {
        R.color.card
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = color)),
        ) {
            Column(
                modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    task.name?.let {
                        Text(
                            text = it, fontSize = 25.sp, fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = { onDeleteSubTas() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                task.description?.let {
                    Text(
                        text = it, fontSize = 20.sp, fontWeight = FontWeight.W500
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Created At: $created", fontSize = 20.sp, fontWeight = FontWeight.W500
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "End Date: $end", fontSize = 20.sp, fontWeight = FontWeight.W500)
                if (task.completed == true) {
                    val comp = task.completedDate?.let {
                        "${it.substringBefore("T")} ${it.substringAfter("T").substringBefore(".")}"
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Completed At: $comp", fontSize = 20.sp, fontWeight = FontWeight.W500
                    )
                }
            }

        }

    }
}