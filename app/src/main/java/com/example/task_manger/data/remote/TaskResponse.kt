package com.example.task_manger.data.remote

import com.example.task_manger.domian.model.Task

data class TaskResponse(
    val tasks: List<Task>
)