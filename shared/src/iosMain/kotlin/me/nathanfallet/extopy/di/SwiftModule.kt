package me.nathanfallet.extopy.di

import me.nathanfallet.extopy.database.DatabaseDriverFactory
import me.nathanfallet.extopy.models.application.ExtopyEnvironment
import me.nathanfallet.extopy.repositories.application.ITokenRepository

object SwiftModule {

    lateinit var environment: ExtopyEnvironment
    lateinit var tokenRepository: ITokenRepository

    fun module() = org.koin.dsl.module {
        single { DatabaseDriverFactory() }

        single { environment }
        single { tokenRepository }
    }

}
