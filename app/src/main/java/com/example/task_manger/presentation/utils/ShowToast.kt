package com.example.task_manger.presentation.utils

import android.app.Activity
import android.widget.Toast


fun showToastMessage(message: String,activity: Activity) {
    activity.showToast(message)
}

fun Activity.showToast(message: String) {
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(this, message, duration)
    toast.show()
}