package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.model.InputTask
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.repository.TaskRepository

class PostTask(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: InputTask):Message {
       return taskRepository.postTask(task)
    }
}