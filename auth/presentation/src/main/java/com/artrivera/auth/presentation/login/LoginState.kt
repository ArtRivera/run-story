package com.artrivera.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoginAvailable: Boolean = false,
    val isLoading: Boolean = false,
) {
    val canLogin: Boolean
        get() = isLoginAvailable && !isLoading
}
