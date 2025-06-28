package com.extopy

import android.app.Application
import com.extopy.di.initializeSentry
import com.extopy.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class ExtopyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@ExtopyApplication)
            modules(sharedModule + androidModule)
        }
        if (!BuildConfig.DEBUG) {
            initializeSentry()
        }
    }

}
