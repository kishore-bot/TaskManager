package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.model.Task
import com.example.task_manger.domian.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTask(
    private val taskRepository:TaskRepository
) {
     operator fun invoke(status :String):Flow<List<Task>>{
        return taskRepository.getTask(status)
    }

}