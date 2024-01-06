package com.example.task_manger.presentation.add_sub_task

import com.example.task_manger.domian.model.InputSubTask


sealed class AddSubTaskEvent {
    data class AddTaskId(val taskId: String) : AddSubTaskEvent()
    data class AddSubTask(val subTask: InputSubTask) : AddSubTaskEvent()
    data object PostSubTask : AddSubTaskEvent()
}