package com.example.task_manger.presentation.main_activity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_manger.domian.usecase.AppEntryUseCases
import com.example.task_manger.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
) : ViewModel() {
    var splashCondition by mutableStateOf(true)
        private set
    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        viewModelScope.launch {
            appEntryUseCases.readAppEntry().collect { entry ->
                Log.d("Test", entry)
                startDestination = if (entry == "" || entry == "null" || entry.isEmpty()) {
                    Route.AppStartNavigation.route
                } else {
                    Route.TaskNavigation.route
                }
                delay(300)
                splashCondition = false
            }
        }
    }
}