package com.extopy.di

import com.extopy.database.DatabaseDriverFactory
import com.extopy.database.IDatabaseDriverFactory
import com.extopy.models.application.ExtopyEnvironment
import dev.kaccelero.repositories.INativeSettingsRepository
import dev.kaccelero.repositories.KeychainRepository
import dev.kaccelero.repositories.NativeSettingsRepository
import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults

object SwiftModule {

    fun module() = org.koin.dsl.module {
        single<ExtopyEnvironment> {
            if (NSBundle.mainBundle.bundleIdentifier?.endsWith(".dev") == true) ExtopyEnvironment.DEVELOPMENT
            else ExtopyEnvironment.PRODUCTION
        }
        single<INativeSettingsRepository> {
            val group =
                if (get<ExtopyEnvironment>() == ExtopyEnvironment.DEVELOPMENT) "group.me.nathanfallet.Extopy.dev"
                else "group.me.nathanfallet.Extopy"
            NativeSettingsRepository(
                userDefaults = NSUserDefaults(suiteName = group),
                keychainRepository = KeychainRepository(service = null, keychainAccessGroup = group)
            )
        }
        single<IDatabaseDriverFactory> { DatabaseDriverFactory() }
    }

}
