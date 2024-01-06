package com.example.task_manger.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.task_manger.domian.usecase.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeStatus -> {
                _state.value = _state.value.copy(status = event.status)
            }

            is HomeEvent.GetTask -> {
                getTask()

            }

            is HomeEvent.GetStars -> {
                viewModelScope.launch {
                    val stars = getStars()
                    stars.collect { starValue ->
                        _state.value = _state.value.copy(stars = starValue)
                    }
                }
            }

        }
    }

    private fun getTask() {
        val tasks = taskUseCases.getTask(
            status = _state.value.status
        ).catch { viewModelScope }
        _state.value = _state.value.copy(tasks = tasks)
    }

    private fun getStars(): Flow<String> {
        return taskUseCases.getStars()
    }
}

