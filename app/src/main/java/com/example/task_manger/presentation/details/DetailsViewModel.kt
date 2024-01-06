package com.example.task_manger.presentation.details


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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state

    private val _completedRes = MutableLiveData<String>()
    val completedRes: LiveData<String> = _completedRes

    private val _deletedMes = MutableLiveData<String>()
    val deletedMes: LiveData<String> = _deletedMes

    private val _deletedSubMes = MutableLiveData<String>()
    val deletedSubMes: LiveData<String> = _deletedSubMes

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpdateTaskId -> {
                _state.value = _state.value.copy(taskId = event.taskId)
            }

            is DetailsEvent.GetTask -> {
                getTask()

            }

            is DetailsEvent.UpdateSubTaskId -> {
                _state.value = _state.value.copy(subTaskId = event.subTaskId)
            }

            is DetailsEvent.PostCompleteTask -> {
                viewModelScope.launch {
                    val message = postTaskComplete()
                    _completedRes.postValue(message.message)
                }
            }

            is DetailsEvent.DeleteTask -> {
                viewModelScope.launch {
                    val message = deleteTask()
                    _deletedMes.postValue(message.message)
                }
            }

            is DetailsEvent.DeleteSubTask -> {
                viewModelScope.launch {
                    val messages = deleteSubTask()
                    delay(200)
                    _deletedSubMes.postValue(messages.message)
                }
            }

            is DetailsEvent.ClearString -> {
                resetCompletedMessage()
            }
        }
    }

    private fun getTask() {
        val tasks = taskUseCases.getSingleTask(
            id = _state.value.taskId
        ).catch { viewModelScope }
        _state.value = _state.value.copy(tasks = tasks)
    }

    private suspend fun postTaskComplete(): Message {
        return taskUseCases.postTaskComplete(
            taskId = _state.value.taskId, subTaskId = _state.value.subTaskId
        )
    }

    private suspend fun deleteTask(): Message {
        return taskUseCases.deleteTask(
            taskId = _state.value.taskId,
        )
    }

    private suspend fun deleteSubTask(): Message {
        return taskUseCases.deleteSubTask(
            taskId = _state.value.taskId, subTaskId = _state.value.subTaskId
        )
    }

    private fun resetCompletedMessage() {
        _completedRes.value = ""
        _deletedSubMes.value=""
    }
}