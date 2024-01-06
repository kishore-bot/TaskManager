package com.example.task_manger.domian.usecase.userRemote

import com.example.task_manger.data.remote.UserCredentials
import com.example.task_manger.domian.model.User
import com.example.task_manger.domian.repository.UserRepository

class UserLogin(private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): UserCredentials {
        return userRepository.userLogin(user)
    }
}