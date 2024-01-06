package com.example.task_manger.presentation.add_sub_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_manger.domian.model.Message
import com.example.task_manger.domian.usecase.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSubTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(AddSubTaskState())
    val state: State<AddSubTaskState> = _state

    private val _completedRes = MutableLiveData<String>()
    val completedRes: LiveData<String> = _completedRes


     fun onEvent(event: AddSubTaskEvent) {
        when (event) {
            is AddSubTaskEvent.AddTaskId -> {
                _state.value = _state.value.copy(taskId = event.taskId)
            }

            is AddSubTaskEvent.AddSubTask -> {
                _state.value = _state.value.copy(subTask = event.subTask)

            }

            is AddSubTaskEvent.PostSubTask -> {
                viewModelScope.launch {
                    val messages = postTask()
                    delay(200)
                    if (messages != null) {
                        _completedRes.postValue(messages.message)
                    }
                }
            }
        }
    }

    private suspend fun postTask(): Message? {
        return  _state.value.subTask?.let {
            _state.value.taskId?.let { it1 ->
                taskUseCases.addSubTask(
                    taskId = it1,
                    subTask = it
                )
            }
        }
    }
}