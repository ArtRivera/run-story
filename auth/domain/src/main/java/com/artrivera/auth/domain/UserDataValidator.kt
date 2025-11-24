package com.artrivera.auth.domain

class UserDataValidator(private val patternValidator: PatternValidator) {


    fun isValidEmail(email: String): Boolean = patternValidator.matches(email)


    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasUpperCaseChar = password.any { it.isUpperCase() }
        val hasLowerCaseChar = password.any { it.isLowerCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasDigit = hasDigit,
            hasUpperCaseChar = hasUpperCaseChar,
            hasLowerCaseChar = hasLowerCaseChar,
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}