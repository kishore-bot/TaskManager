package com.example.task_manger.domian.repository

import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.domian.model.InputTask
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTask(status: String): Flow<List<Task>>

    suspend fun postTask(task: InputTask): Message

    fun getSingleTask(id: String): Flow<Task>

    suspend fun postTaskComplete(taskId: String, subTaskId: String): Message

    fun getUserStars(): Flow<String>

    suspend fun deleteTask(taskId: String): Message

    suspend fun deleteSubTask(taskId: String,subTaskId: String): Message


    suspend fun addSubTask(taskId: String,subTask: InputSubTask): Message

}