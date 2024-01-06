package com.example.task_manger.domian.usecase

import com.example.task_manger.domian.usecase.app_entry.ReadAppEntry
import com.example.task_manger.domian.usecase.app_entry.SaveAppEntry

data class AppEntryUseCases (
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)