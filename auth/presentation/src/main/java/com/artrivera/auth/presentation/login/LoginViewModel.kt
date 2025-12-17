package com.artrivera.auth.presentation.login

import androidx.lifecycle.ViewModel
import com.artrivera.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())

    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction){
        when(action){
           is LoginAction.OnTogglePasswordVisibility -> {
                val isPasswordVisible = _state.value.isPasswordVisible
                _state.value =
                    _state.value.copy(isPasswordVisible = !isPasswordVisible)
            }
            is LoginAction.OnChangeEmail -> {
                _state.value = _state.value.copy(email = action.email)
            }
            else -> Unit
        }
    }
}