package com.artrivera.core.data.di

import com.artrivera.core.data.HttpClientFactory
import com.artrivera.core.data.session.EncryptedSessionStorage
import com.artrivera.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}