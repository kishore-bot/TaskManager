package com.example.task_manger.domian.usecase.taskRemote

import com.example.task_manger.domian.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetStars (
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow <String> {
        return taskRepository.getUserStars()
    }

}