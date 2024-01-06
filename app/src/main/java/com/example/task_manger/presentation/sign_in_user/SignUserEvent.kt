package com.example.task_manger.presentation.sign_in_user

import com.example.task_manger.domian.model.User

sealed class SignUserEvent {
    data class GetUser(val user: User) : SignUserEvent()
    data object SignUser : SignUserEvent()
}