package com.example.task_manger.data.repository


import com.example.task_manger.data.remote.TaskManagerApi
import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.domian.model.InputTask
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.model.Task
import com.example.task_manger.domian.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepoImp(
    private val taskManagerApi: TaskManagerApi,
) : TaskRepository {
    override fun getTask(status: String): Flow<List<Task>> {
        return try {
            flow {
                emit(taskManagerApi.getTask(status).tasks)
            }
        } catch (e: Exception) {
            flow {
                throw e // Emit a Task with an error message
            }
        }
    }

    override suspend fun postTask(task: InputTask): Message {
        return try {
            taskManagerApi.postTask(task)
        } catch (e: Exception) {
            Message(message = "Some Thing Went Wrong")
        }
    }

    override fun getSingleTask(id: String): Flow<Task> {
        return try {
            flow {
                emit(taskManagerApi.getSingleTask(id))
            }
        } catch (e: Exception) {
            flow {
                throw e // Emit a Task with an error message
            }
        }
    }

    override suspend fun postTaskComplete(taskId: String, subTaskId: String): Message {
        return try {
            taskManagerApi.postTaskComplete(taskId, subTaskId)
        } catch (e: Exception) {
            Message(message = "Some Thing Went Wrong")
        }
    }

    override fun getUserStars(): Flow<String> {
        return flow {
            val userStarsResponse = taskManagerApi.getUserStars()
            emit(userStarsResponse.star)
        }
    }

    override suspend fun deleteTask(taskId: String): Message {
        return try {
            taskManagerApi.deleteTask(taskId = taskId)
        } catch (e: Exception) {
            Message(message = "Error")
        }
    }

    override suspend fun deleteSubTask(taskId: String, subTaskId: String): Message {
        return try {

            taskManagerApi.deleteSubTask(taskId = taskId, subTaskId = subTaskId)
        } catch (e: Exception) {
            Message(message = "Error")
        }
    }

    override suspend fun addSubTask(taskId: String, subTask: InputSubTask): Message {
        return try {
            taskManagerApi.addSubTask(taskId = taskId, subTask = subTask)
        } catch (e: Exception) {
            Message(message = "Some Thing Went Wrong")
        }
    }
}
