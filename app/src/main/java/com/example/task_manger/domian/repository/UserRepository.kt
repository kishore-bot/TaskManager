package com.example.task_manger.domian.repository

import com.example.task_manger.data.remote.UserCredentials
import com.example.task_manger.domian.model.User

interface UserRepository {
    suspend fun  userSign(user: User): UserCredentials
    suspend fun  userLogin(user:User): UserCredentials
}