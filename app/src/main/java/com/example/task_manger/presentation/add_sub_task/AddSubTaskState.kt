package com.example.task_manger.presentation.add_sub_task

import com.example.task_manger.domian.model.InputSubTask


data class AddSubTaskState(
    val taskId: String? = "",
    val subTask: InputSubTask? = null,
)
