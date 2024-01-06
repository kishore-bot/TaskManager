package com.example.task_manger.presentation.log_in_user

import com.example.task_manger.domian.model.User

sealed class LogInEvent {
    data class GetUser(val user: User):LogInEvent()
    data object LodInUser:LogInEvent()
}