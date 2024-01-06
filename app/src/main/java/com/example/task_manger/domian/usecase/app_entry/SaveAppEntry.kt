package com.example.task_manger.domian.usecase.app_entry

import com.example.task_manger.domian.manager.LocalUserManager

class SaveAppEntry (
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(token: String) {
        localUserManager.saveAppEntry(token = token)
    }
}