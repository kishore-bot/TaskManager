package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.repository.TaskRepository

class PostTaskComplete(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String,subTaskId:String): Message {
        return taskRepository.postTaskComplete(taskId = taskId, subTaskId = subTaskId)
    }
}