package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.model.Task
import com.example.task_manger.domian.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetSingleTask (
private val taskRepository: TaskRepository
) {
    operator fun invoke(id :String): Flow<Task> {
        return taskRepository.getSingleTask(id)
    }

}