package com.example.task_manger.domian.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry(token :String)
    fun readAppEntry(): Flow<String>
}