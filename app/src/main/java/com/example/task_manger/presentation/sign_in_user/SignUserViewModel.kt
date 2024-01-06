package com.example.task_manger.presentation.sign_in_user

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
class SignUserViewModel  @Inject constructor(
    private val userUseCases: UserUseCases,
    private val appEntryUseCases: AppEntryUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(SignState())
    val state: State<SignState> = _state

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    fun onEvent(event: SignUserEvent) {
        when (event) {
            is SignUserEvent.GetUser -> {
                _state.value = _state.value.copy(user = event.user)
            }

            is SignUserEvent.SignUser -> {
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
            userUseCases.userSign(
                user = it
            )

        }
    }

}