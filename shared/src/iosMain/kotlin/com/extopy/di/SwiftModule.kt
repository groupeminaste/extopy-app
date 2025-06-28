package com.extopy.di

import com.extopy.database.DatabaseDriverFactory
import com.extopy.database.IDatabaseDriverFactory
import com.extopy.models.application.ExtopyEnvironment
import dev.kaccelero.repositories.INativeSettingsRepository
import dev.kaccelero.repositories.KeychainRepository
import dev.kaccelero.repositories.NativeSettingsRepository
import platform.Foundation.NSUserDefaults

object SwiftModule {

    lateinit var environment: ExtopyEnvironment

    fun module() = org.koin.dsl.module {
        single<IDatabaseDriverFactory> { DatabaseDriverFactory() }
        single<INativeSettingsRepository> {
            val group =
                if (environment == ExtopyEnvironment.DEVELOPMENT) "group.me.nathanfallet.Extopy.dev"
                else "group.me.nathanfallet.Extopy"
            NativeSettingsRepository(
                userDefaults = NSUserDefaults(suiteName = group),
                keychainRepository = KeychainRepository(service = null, keychainAccessGroup = group)
            )
        }

        single { environment }
    }

}
