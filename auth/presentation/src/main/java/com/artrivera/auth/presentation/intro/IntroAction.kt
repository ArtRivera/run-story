package com.artrivera.auth.presentation.intro

sealed interface IntroAction {

    object OnSignInClick: IntroAction

    object OnSignUpClick: IntroAction

}