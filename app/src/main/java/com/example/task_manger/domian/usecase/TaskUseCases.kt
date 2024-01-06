package com.example.task_manger.domian.usecase

import com.example.task_manger.domian.usecase.taskRemote.AddSubTask
import com.example.task_manger.domian.usecase.taskRemote.DeleteSubTask
import com.example.task_manger.domian.usecase.taskRemote.DeleteTask
import com.example.task_manger.domian.usecase.taskRemote.GetSingleTask
import com.example.task_manger.domian.usecase.taskRemote.GetStars
import com.example.task_manger.domian.usecase.taskRemote.GetTask
import com.example.task_manger.domian.usecase.taskRemote.PostTask
import com.example.task_manger.domian.usecase.taskRemote.PostTaskComplete

data class TaskUseCases(
    val getTask: GetTask,
    val postTask: PostTask,
    val getSingleTask: GetSingleTask,
    val postTaskComplete: PostTaskComplete,
    val getStars: GetStars,
    val deleteTask: DeleteTask,
    val deleteSubTask: DeleteSubTask,
    val addSubTask: AddSubTask
)