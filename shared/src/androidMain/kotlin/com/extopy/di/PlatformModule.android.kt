package com.extopy.di

import android.content.Context
import com.extopy.data.database.ExtopyDatabase
import com.extopy.data.database.buildExtopyDatabase
import com.extopy.data.database.getDatabaseBuilder
import com.extopy.domain.models.application.ExtopyEnvironment
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android platform module: the database, both settings stores, and which deployment this build
 * talks to.
 *
 * [environment] is passed in because it comes from the product flavor, which only the application
 * module can read.
 *
 * Android has no keychain, so the session lives in its own preferences file rather than in a
 * different kind of store — same as before, only under a name that says what it holds.
 */
fun platformModule(context: Context, environment: ExtopyEnvironment): Module = module {
    single { environment }

    single<ExtopyDatabase> { buildExtopyDatabase(getDatabaseBuilder(context)) }

    single<Settings> {
        SharedPreferencesSettings(context.getSharedPreferences("extopy", Context.MODE_PRIVATE))
    }
    single<Settings>(SecureSettings) {
        SharedPreferencesSettings(context.getSharedPreferences("extopy_session", Context.MODE_PRIVATE))
    }
}
