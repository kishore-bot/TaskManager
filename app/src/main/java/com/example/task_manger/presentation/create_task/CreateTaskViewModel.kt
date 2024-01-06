package com.example.task_manger.presentation.create_task


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.usecase.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
) : ViewModel() {
    private var _state = mutableStateOf(CreateTaskState())
    val state: State<CreateTaskState> = _state
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result
    fun onEvent(event: CreateTaskEvent) {
        when (event) {
            is CreateTaskEvent.ChangeTask -> {
                _state.value = _state.value.copy(task = event.task)
            }

            is CreateTaskEvent.PostTask -> {
                viewModelScope.launch {
                    val message = postTask()?.message.toString()
                    _result.postValue(message)
                }

            }
        }
    }

    private suspend fun postTask(): Message? {
        return _state.value.task?.let {
            taskUseCases.postTask(
                task = it
            )

        }
    }

}
