package com.example.task_manger.domian.usecase

import com.example.task_manger.domian.usecase.userRemote.UserLogin
import com.example.task_manger.domian.usecase.userRemote.UserSign


data class UserUseCases(
    val userSign: UserSign,
    val userLogin: UserLogin

)