package com.extopy.di

import com.extopy.data.database.ExtopyDatabase
import com.extopy.data.database.buildExtopyDatabase
import com.extopy.data.database.getDatabaseBuilder
import com.extopy.domain.models.application.ExtopyEnvironment
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSProcessInfo
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

/**
 * The keychain service the session is stored under.
 *
 * Not shared with anything: sharing keychain items needs a `keychain-access-groups` entitlement,
 * which this app does not declare. The App Group it *does* declare is a different capability and
 * does not grant it — the day a widget needs to read the session, that entitlement is what has to
 * be added.
 */
private const val KEYCHAIN_SERVICE = "com.extopy.session"

/**
 * Set `EXTOPY_ENV=local` in the scheme's environment variables to run a debug build against a
 * backend on your own machine. Ignored in release builds, so it cannot ship by accident.
 */
private const val ENV_VARIABLE = "EXTOPY_ENV"

/** iOS platform module: the database, the session store, and which deployment this build talks to. */
@OptIn(ExperimentalSettingsImplementation::class)
fun platformModule(): Module = module {
    single { currentEnvironment() }

    single<ExtopyDatabase> { buildExtopyDatabase(getDatabaseBuilder()) }

    // The session is the only thing the app stores, and it is made of credentials, so it goes in the
    // keychain. The service is per-environment so that the development app cannot pick up the
    // production session. Anything non-sensitive added later wants its own, unqualified, definition
    // on the App Group user defaults rather than a second use of this one.
    single<Settings>(SecureSettings) {
        KeychainSettings(keychainService(get()))
    }
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

/** The keychain service of this build, so the environments cannot read each other's session. */
private fun keychainService(environment: ExtopyEnvironment): String =
    if (environment == ExtopyEnvironment.PRODUCTION) KEYCHAIN_SERVICE
    else "$KEYCHAIN_SERVICE.${environment.name.lowercase()}"
