package com.example.task_manger.data.remote

import com.example.task_manger.domian.model.InputSubTask
import com.example.task_manger.domian.model.InputTask
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.model.Task
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface TaskManagerApi {
    @GET("/task")
    suspend fun getTask(@Query("status") status: String): TaskResponse

    @POST("/task")
    suspend fun postTask(
        @Body inputTask: InputTask,
    ): Message

    @GET("/task/{id}")
    suspend fun getSingleTask(
        @Path("id") id: String,
    ): Task

    @PATCH("/complete/task/{taskId}/sub-task/{subTaskId}")
    suspend fun postTaskComplete(
        @Path("taskId") taskId: String,
        @Path("subTaskId") subTaskId: String = "0",
    ): Message

    @GET("/stars")
    suspend fun getUserStars(): UserStars

    @DELETE("/task/{taskId}")
    suspend fun deleteTask(
        @Path("taskId") taskId: String,
    ): Message

    @DELETE("/task/{taskId}/sub-task/{subTaskId}")
    suspend fun deleteSubTask(
        @Path("taskId") taskId: String,
        @Path("subTaskId") subTaskId: String,
    ): Message

    @POST("/task/{taskId}/sub-task")
    suspend fun addSubTask(
        @Path("taskId") taskId: String,
        @Body subTask: InputSubTask,
    ): Message
}