package com.example.task_manger.presentation.log_in_user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_manger.data.remote.UserCredentials
import com.example.task_manger.domian.usecase.AppEntryUseCases
import com.example.task_manger.domian.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val appEntryUseCases: AppEntryUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(LogInState())
    val state: State<LogInState> = _state

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.GetUser -> {
                _state.value = _state.value.copy(user = event.user)
            }

            is LogInEvent.LodInUser -> {
                viewModelScope.launch {
                    val credentials = userSignIn()
                    if (credentials != null) {
                        if (credentials.token != null) {
                            appEntryUseCases.saveAppEntry(token = credentials.token)
                        }
                        _result.postValue(credentials.message)
                    }
                    delay(300)
                }

            }
        }
    }

    private suspend fun userSignIn(): UserCredentials? {
        return _state.value.user?.let {
            userUseCases.userLogin(
                user = it
            )

        }
    }

}
