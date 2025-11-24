package com.artrivera.auth.presentation.di

import com.artrivera.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
}