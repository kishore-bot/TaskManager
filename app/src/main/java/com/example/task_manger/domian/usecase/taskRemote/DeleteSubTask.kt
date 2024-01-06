package com.example.task_manger.domian.usecase.taskRemote

import android.util.Log
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.repository.TaskRepository

class DeleteSubTask(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(taskId: String, subTaskId: String): Message {
        return taskRepository.deleteSubTask(taskId = taskId, subTaskId = subTaskId)
    }
}