package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.repository.TaskRepository

class DeleteTask(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(taskId: String): Message {
        return taskRepository.deleteTask(taskId = taskId)
    }
}