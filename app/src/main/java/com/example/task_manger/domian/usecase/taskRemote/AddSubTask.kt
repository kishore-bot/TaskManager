package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.repository.TaskRepository

class AddSubTask(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(taskId: String, subTask: InputSubTask): Message {
        return taskRepository.addSubTask(taskId = taskId, subTask = subTask)
    }
}