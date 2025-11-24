package com.artrivera.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artrivera.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class RegisterViewModel(private val userDataValidator: UserDataValidator) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    init {
        observeEmailWithDebounce()
        observePasswordWithDebounce()
    }

    // Validate email after user stops typing for 500ms
    private fun observeEmailWithDebounce() {
        viewModelScope.launch {
            _state
                .distinctUntilChangedBy { it.email }
                .debounce(500)  // Wait 500ms after last change
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
                .debounce(500)  // Wait 500ms after last change
                .collect { it ->
                    val newPasswordValidationState = userDataValidator.validatePassword(it.password)
                    _state.value = _state.value.copy(
                        passwordValidationState = newPasswordValidationState
                    )
                }
        }
    }

    fun onAction(action: RegisterAction) {
    }

    fun onChangeEmail(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onChangePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }


}