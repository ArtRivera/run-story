package com.artrivera.auth.presentation.register

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityClick : RegisterAction
    data object OnLoginClick : RegisterAction
    data object OnRegisterClick : RegisterAction

    data class OnChangeEmail(val email: String) : RegisterAction

    data class OnChangePassword(val password: String) : RegisterAction
}