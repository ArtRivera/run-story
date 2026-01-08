package com.artrivera.auth.presentation.login

sealed interface LoginAction {
    data object OnTogglePasswordVisibility: LoginAction
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction

    data class OnChangeEmail(val email: String) : LoginAction

    data class OnChangePassword(val password: String): LoginAction
}