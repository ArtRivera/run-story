package com.artrivera.runstory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artrivera.core.domain.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private var _state = MutableStateFlow(MainState())

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isCheckingAuth = true) }
            _state.update { it.copy(isLoggedIn = sessionStorage.get() != null) }
            _state.update { it.copy(isCheckingAuth = false) }
        }
    }

}