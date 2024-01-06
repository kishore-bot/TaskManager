package com.example.task_manger.presentation.home

import com.example.task_manger.domian.model.Task
import  kotlinx.coroutines.flow.Flow

data class HomeState(
    val status : String="inProgress",
    val tasks : Flow<List<Task>>?=null,
    val stars : String?=null
)