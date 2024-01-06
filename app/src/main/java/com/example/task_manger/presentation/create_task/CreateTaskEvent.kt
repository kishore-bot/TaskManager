package com.example.task_manger.presentation.create_task

import com.example.task_manger.domian.model.InputTask

sealed class CreateTaskEvent {
    data class ChangeTask(val task: InputTask) : CreateTaskEvent()
    data object PostTask : CreateTaskEvent()
}