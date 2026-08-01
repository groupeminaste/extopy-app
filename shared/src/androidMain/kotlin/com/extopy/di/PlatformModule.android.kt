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
 * Android platform module: the database, the session store, and which deployment this build talks to.
 *
 * [environment] is passed in because it comes from the product flavor, which only the application
 * module can read.
 */
fun platformModule(context: Context, environment: ExtopyEnvironment): Module = module {
    single { environment }

    single<ExtopyDatabase> { buildExtopyDatabase(getDatabaseBuilder(context)) }

    // The session is the only thing the app stores, and it gets a preferences file of its own so
    // that signing out cannot take anything else with it. Anything non-sensitive added later wants
    // its own, unqualified, definition rather than a second use of this one.
    //
    // Each flavor is a separate application id, so the environments already cannot see each other.
    single<Settings>(SecureSettings) {
        SharedPreferencesSettings(context.getSharedPreferences("extopy_session", Context.MODE_PRIVATE))
    }
}
