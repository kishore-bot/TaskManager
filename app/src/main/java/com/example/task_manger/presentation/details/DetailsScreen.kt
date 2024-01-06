package com.example.task_manger.presentation.details

import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.task_manger.R
import com.example.task_manger.domian.model.Task
import com.example.task_manger.presentation.details.components.MainTaskCard
import com.example.task_manger.presentation.details.components.SubTaskCard
import com.example.task_manger.presentation.navgraph.Route
import com.example.task_manger.presentation.utils.ProgressSection
import com.example.task_manger.presentation.utils.TaskButton
import com.example.task_manger.presentation.utils.TaskTopBar
import com.example.task_manger.presentation.utils.showToastMessage
import kotlinx.coroutines.delay


@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    taskId: String,
    onBackClick: () -> Unit,
    navController: NavController,
) {

    val activity = LocalContext.current as? Activity
    LaunchedEffect(Unit) {
        viewModel.onEvent(DetailsEvent.UpdateTaskId(taskId = taskId))
        viewModel.onEvent(DetailsEvent.GetTask)
    }
    val state = viewModel.state.value
    val taskState = state.tasks?.collectAsState(initial = null)
    val task: Task? = taskState?.value
    val completedMes by viewModel.completedRes.observeAsState(initial = "")
    val deleteMsg by viewModel.deletedMes.observeAsState(initial = "")
    val deleteSubMsg by viewModel.deletedSubMes.observeAsState(initial = "")
    LaunchedEffect(completedMes) {
        if (completedMes != "Error" && completedMes != "") {
            if (activity != null) {
                showToastMessage(activity = activity, message = completedMes)
            }
            delay(200)
            viewModel.onEvent(DetailsEvent.GetTask)
            viewModel.onEvent(DetailsEvent.ClearString)
        }
    }
    LaunchedEffect(deleteMsg) {
        if (deleteMsg != "") {
            if (activity != null) {
                showToastMessage(activity = activity, message = deleteMsg)
            }
            delay(400)
            onBackClick()
        }
    }
    LaunchedEffect(deleteSubMsg) {
        if (deleteSubMsg != "" && deleteMsg != "Error") {
            if (activity != null) {
                showToastMessage(activity = activity, message = deleteSubMsg)
            }
            delay(200)
            viewModel.onEvent(DetailsEvent.GetTask)
            viewModel.onEvent(DetailsEvent.ClearString)
        }
    }
    Scaffold(modifier = Modifier.statusBarsPadding(),
        containerColor = (colorResource(R.color.backGround)),
        topBar = {
            TaskTopBar(title = "Task Details", onBackClick = { onBackClick() })
        }) {
        it.calculateBottomPadding()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            item {
                if (task != null) {
                    MainTaskCard(task = task, modifier = Modifier.padding(10.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (task != null) {
                    ProgressSection(
                        task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 20.dp),
                        modifier1 = Modifier
                            .width(300.dp)
                            .padding(top = 3.dp)
                    )
                }
            }
            item {
                if (task != null) {
                    if (!task.completed) {
                        TaskButton(
                            title = "Make As Done",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {

                            val subTaskId: String = task.nextSubtask ?: "0"
                            val mainTaskId = task.id
                            postToComplete(
                                viewModel = viewModel,
                                mainTaskId = mainTaskId,
                                subTaskId = subTaskId
                            )
                        }
                    }
                }
            }
            item {
                if (task != null) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(10.dp), onClick = {
                            val mainTaskId = task.id
                            deleteTask(
                                viewModel = viewModel,
                                mainTaskId = mainTaskId,
                            )
                        }, shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Delete Task", fontSize = 20.sp)
                    }
                }
            }
            if (task != null) {
                subList(task, viewModel = viewModel)
            }
            item {
                if (task != null) {
                    if (task.subtask?.isEmpty() == true && !task.completed) {
                        Text(
                            text = "No Sub Task Found",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            item {
                if (task != null) {
                    if (!task.completed) {
                        TaskButton(title = "Add Sub Task", modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)) {
                            taskId.let { taskID ->
                                navController.navigate(Route.AddSubTaskScreen.route + "/$taskID")
                            }
                        }
                    }
                }
            }
            item { 
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

}

private fun postToComplete(
    viewModel: DetailsViewModel,
    subTaskId: String,
    mainTaskId: String,
) {
    viewModel.onEvent(DetailsEvent.UpdateTaskId(taskId = mainTaskId))
    viewModel.onEvent(DetailsEvent.UpdateSubTaskId(subTaskId = subTaskId))
    viewModel.onEvent(DetailsEvent.PostCompleteTask).toString()
}

private fun deleteTask(
    viewModel: DetailsViewModel,
    mainTaskId: String,
) {
    viewModel.onEvent(DetailsEvent.UpdateTaskId(taskId = mainTaskId))
    viewModel.onEvent(DetailsEvent.DeleteTask).toString()
}

private fun deleteSubTask(
    viewModel: DetailsViewModel,
    subTaskId: String,
    mainTaskId: String,
) {
    viewModel.onEvent(DetailsEvent.UpdateTaskId(taskId = mainTaskId))
    viewModel.onEvent(DetailsEvent.UpdateSubTaskId(subTaskId = subTaskId))
    viewModel.onEvent(DetailsEvent.DeleteSubTask).toString()
}

fun LazyListScope.subList(task: Task, viewModel: DetailsViewModel) {
    if (task.subtask?.isNotEmpty() == true) {
        items(task.subtask.size) {
            SubTaskCard(task = task.subtask[it], onDeleteSubTas = {
                val mainTaskId = task.id
                val subTaskId: String? = task.subtask[it]._id
                if (subTaskId != null) {
                    deleteSubTask(
                        viewModel = viewModel, mainTaskId = mainTaskId, subTaskId = subTaskId
                    )
                }
            })
        }
    }
}

