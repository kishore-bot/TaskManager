package com.example.task_manger.domian.model

data class InputTask(
    val title: String,
    val description: String,
    val endTime: String,
    val subtasks: List<com.example.task_manger.domian.model.InputSubTask>? = emptyList(),
    val priority: String
)

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}

