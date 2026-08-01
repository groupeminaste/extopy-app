package com.extopy.di

import com.extopy.data.database.ExtopyDatabase
import com.extopy.data.database.buildExtopyDatabase
import com.extopy.data.database.getDatabaseBuilder
import com.extopy.domain.models.application.ExtopyEnvironment
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSUserDefaults
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

/** App Group shared by the app and its (future) widget/extensions. */
const val APP_GROUP = "group.me.nathanfallet.Extopy"

/**
 * Set `EXTOPY_ENV=local` in the scheme's environment variables to run a debug build against a
 * backend on your own machine. Ignored in release builds, so it cannot ship by accident.
 */
private const val ENV_VARIABLE = "EXTOPY_ENV"

/** iOS platform module: the database, both settings stores, and which deployment this build talks to. */
@OptIn(ExperimentalSettingsImplementation::class)
fun platformModule(): Module = module {
    single { currentEnvironment() }

    single<ExtopyDatabase> { buildExtopyDatabase(getDatabaseBuilder()) }

    single<Settings> {
        // The App Group suite, so a widget/extension can read what we store. Falls back to standard
        // defaults if the App Group capability is not enabled.
        NSUserDefaultsSettings(
            NSUserDefaults(suiteName = appGroup(get())) ?: NSUserDefaults.standardUserDefaults
        )
    }
    // Tokens are credentials, so they go in the keychain rather than in user defaults.
    single<Settings>(SecureSettings) { KeychainSettings(appGroup(get())) }
}

/**
 * Which deployment this build talks to.
 *
 * Read off the bundle identifier — the staging build is the same app with a `.dev` suffix — so
 * nothing has to be passed in from Swift.
 */
@OptIn(ExperimentalNativeApi::class)
private fun currentEnvironment(): ExtopyEnvironment {
    if (Platform.isDebugBinary &&
        NSProcessInfo.processInfo.environment[ENV_VARIABLE] == "local"
    ) return ExtopyEnvironment.LOCAL

    return if (NSBundle.mainBundle.bundleIdentifier?.endsWith(".dev") == true) ExtopyEnvironment.DEVELOPMENT
    else ExtopyEnvironment.PRODUCTION
}

/** The App Group of this build; the non-production app is a separate app with its own storage. */
private fun appGroup(environment: ExtopyEnvironment): String =
    if (environment == ExtopyEnvironment.PRODUCTION) APP_GROUP else "$APP_GROUP.dev"
