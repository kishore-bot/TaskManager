package com.example.task_manger.data.remote

import com.example.task_manger.domian.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/sign-up")
    suspend fun userSignUp(
        @Body user: User,
    ): UserCredentials

    @POST("/log-in")
    suspend fun userLogin(
        @Body user: User,
    ): UserCredentials
}