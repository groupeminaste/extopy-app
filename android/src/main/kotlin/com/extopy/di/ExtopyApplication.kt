package com.extopy.di

import android.app.Application
import com.extopy.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ExtopyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ExtopyApplication)
            modules(sharedModule + androidModule)
        }
        if (!BuildConfig.DEBUG) {
            initializeSentry()
        }
    }

}
