package com.artrivera.auth.presentation.register

import androidx.compose.ui.res.stringResource
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()


    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()


    init {
        observeEmailWithDebounce()
        observePasswordWithDebounce()
    }

    // Validate email after user stops typing for 500ms
    private fun observeEmailWithDebounce() {
        viewModelScope.launch {
            _state
                .distinctUntilChangedBy { it.email }
                .debounce(200)  // Wait 200ms after last change
                .collect { it ->
                    val isValid = userDataValidator.isValidEmail(it.email)
                    _state.value = _state.value.copy(
                        isEmailValid = isValid
                    )
                }
        }
    }

    private fun observePasswordWithDebounce() {
        viewModelScope.launch {
            _state
                .distinctUntilChangedBy { it.password }
                .debounce(200)  // Wait 200ms after last change
                .collect { it ->
                    val newPasswordValidationState = userDataValidator.validatePassword(it.password)
                    _state.value = _state.value.copy(
                        passwordValidationState = newPasswordValidationState
                    )
                }
        }
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLoginClick -> {
                val isPasswordVisible = _state.value.isPasswordVisible
                _state.value =
                    _state.value.copy(isPasswordVisible = !isPasswordVisible)
            }

            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> Unit
        }
    }

    fun onChangeEmail(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onChangePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    private fun register() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isRegistering = true)
            val result = repository.register(_state.value.email, _state.value.password)
            _state.value = _state.value.copy(isRegistering = false)

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_email_exists)))
                    } else {
                        eventChannel.send(RegisterEvent.Error(result.error.toText()))
                    }
                }
                is Result.Success -> eventChannel.send(RegisterEvent.RegistrationSuccess)
            }
        }
    }


}