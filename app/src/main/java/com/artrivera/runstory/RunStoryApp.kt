package com.artrivera.runstory

import android.app.Application
import com.artrivera.auth.presentation.di.authViewModelModule
import com.artrivera.core.data.di.coreDataModule
import com.artrivera.runstory.di.appModule
import com.artriveram.auth.data.di.authDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunStoryApp : Application() {


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunStoryApp)
            modules(
                authDataModule,
                authViewModelModule,
                coreDataModule,
                appModule
            )
        }
    }
}