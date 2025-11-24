package com.artrivera.auth.presentation.register

import com.artrivera.auth.domain.PasswordValidationState

data class RegisterState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
) {
    val canRegister: Boolean
        get() = passwordValidationState.isValid && !isRegistering
}
