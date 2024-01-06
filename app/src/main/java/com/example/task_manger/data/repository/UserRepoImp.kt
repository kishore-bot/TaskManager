package com.example.task_manger.data.repository

import com.example.task_manger.data.remote.UserApi
import com.example.task_manger.data.remote.UserCredentials
import com.example.task_manger.domian.model.User
import com.example.task_manger.domian.repository.UserRepository

class UserRepoImp(
    private val userApi: UserApi,
) : UserRepository {
    override suspend fun userSign(user: User): UserCredentials {
        return try {
            userApi.userSignUp(user = user)
        } catch (e: Exception) {
            UserCredentials(message = "Error", token = "")
        }
    }

    override suspend fun userLogin(user: User): UserCredentials {
        return try {
            userApi.userLogin(user = user)
        } catch (e: Exception) {
            UserCredentials(message = "Error", token = "")
        }
    }
}