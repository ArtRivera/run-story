package com.artrivera.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasLowerCaseChar: Boolean = false,
    val hasUpperCaseChar: Boolean = false
) {
    val isValid: Boolean
        get() = hasMinLength && hasDigit && hasUpperCaseChar && hasUpperCaseChar
}
