package com.artrivera.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artrivera.auth.domain.AuthRepository
import com.artrivera.auth.domain.UserDataValidator
import com.artrivera.auth.presentation.R
import com.artrivera.core.domain.utils.DataError
import com.artrivera.core.domain.utils.Result
import com.artrivera.core.presentation.ui.UiText
import com.artrivera.core.presentation.ui.toText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())

    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        validateCanLogin()
    }

    private fun validateCanLogin() {
        viewModelScope.launch {
            _state.onEach {
                val canLogin = userDataValidator.isValidEmail(_state.value.email) && _state.value.password.isNotEmpty()
                _state.value = _state.value.copy(
                    isLoginAvailable = canLogin
                )
            }
                .launchIn(viewModelScope)
        }

    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnTogglePasswordVisibility -> {
                val isPasswordVisible = _state.value.isPasswordVisible
                _state.value =
                    _state.value.copy(isPasswordVisible = !isPasswordVisible)
            }

            is LoginAction.OnChangeEmail -> {
                _state.value = _state.value.copy(email = action.email)
            }

            is LoginAction.OnChangePassword -> {
                _state.value = _state.value.copy(password = action.password)
            }

            LoginAction.OnLoginClick -> login()
            else -> {}
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = authRepository.login(state.value.email.trim(), state.value.password)
            _state.value = _state.value.copy(isLoading = false)

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(LoginEvent.Error(UiText.StringResource(R.string.error_password_incorrect)))
                    } else {
                        eventChannel.send(LoginEvent.Error(result.error.toText()))
                    }
                }

                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}