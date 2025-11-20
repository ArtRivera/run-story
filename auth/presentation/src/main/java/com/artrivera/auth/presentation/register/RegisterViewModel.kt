package com.artrivera.auth.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel: ViewModel() {

    val state = MutableStateFlow<RegisterState>(RegisterState())

    fun onAction(action: RegisterAction){

    }




}