package com.example.task_manger.presentation.details

sealed class DetailsEvent {
    data class UpdateTaskId(val taskId: String) : DetailsEvent()
    data class UpdateSubTaskId(val subTaskId:String): DetailsEvent()
    data object GetTask : DetailsEvent()
    data object PostCompleteTask : DetailsEvent()
    data object DeleteTask : DetailsEvent()
    data object DeleteSubTask : DetailsEvent()
    data object ClearString : DetailsEvent()
}