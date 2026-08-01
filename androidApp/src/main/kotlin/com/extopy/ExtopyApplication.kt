package com.extopy

import android.app.Application
import com.extopy.di.initKoin
import com.extopy.di.initializeSentry
import com.extopy.di.platformModule
import com.extopy.domain.models.application.ExtopyEnvironment

class ExtopyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(platformModule(applicationContext, environment))
        // A crash while developing is not signal worth sending anywhere.
        if (!BuildConfig.DEBUG) initializeSentry()
    }

    /** Which backend this build talks to, decided by the product flavor it was assembled from. */
    private val environment: ExtopyEnvironment
        get() = when (BuildConfig.FLAVOR) {
            "dev" -> ExtopyEnvironment.DEVELOPMENT
            "local" -> ExtopyEnvironment.LOCAL
            else -> ExtopyEnvironment.PRODUCTION
        }

}
