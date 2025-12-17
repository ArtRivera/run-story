package com.artrivera.auth.data.di

import com.artrivera.auth.domain.AuthRepository
import com.artrivera.auth.domain.PatternValidator
import com.artrivera.auth.domain.UserDataValidator
import com.artrivera.auth.data.AuthRepositoryImpl
import com.artrivera.auth.data.EmailPatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}