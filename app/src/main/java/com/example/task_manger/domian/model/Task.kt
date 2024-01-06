package com.example.task_manger.domian.model

data class Task(
    val completed: Boolean,
    val completedDate: String,
    val completedSubtasksCount: Int,
    val createdAt: String,
    val daysLeft: Int,
    val description: String,
    val endTime: String,
    val id: String,
    val nextSubtask: String?,
    val priority: String,
    val subtask: List<com.example.task_manger.domian.model.Subtask>? = emptyList(),
    val timeRemaining: String,
    val title: String,
    val totalSubtasksCount: Int
)