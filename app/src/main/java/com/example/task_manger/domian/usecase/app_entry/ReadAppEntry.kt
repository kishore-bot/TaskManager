package com.example.task_manger.domian.usecase.app_entry

import com.example.task_manger.domian.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localUserManager: LocalUserManager
){
    operator fun invoke(): Flow<String> {
        return localUserManager.readAppEntry()
    }
}