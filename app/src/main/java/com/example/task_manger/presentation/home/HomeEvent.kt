package com.example.task_manger.presentation.home

sealed class HomeEvent {
    data class ChangeStatus(val status: String) : HomeEvent()
    data object GetTask : HomeEvent()
    data object GetStars : HomeEvent()
}