package com.example.task_manger.presentation.details

import com.example.task_manger.domian.model.Task
import kotlinx.coroutines.flow.Flow

data class DetailsState(
    var taskId : String="",
    val subTaskId:String="",
    val tasks : Flow<Task>?=null,

)