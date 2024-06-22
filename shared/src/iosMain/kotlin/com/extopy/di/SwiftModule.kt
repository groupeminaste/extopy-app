package com.extopy.di

import com.extopy.database.DatabaseDriverFactory
import com.extopy.database.IDatabaseDriverFactory
import com.extopy.models.application.ExtopyEnvironment
import com.extopy.repositories.application.ITokenRepository

object SwiftModule {

    lateinit var environment: ExtopyEnvironment
    lateinit var tokenRepository: ITokenRepository

    fun module() = org.koin.dsl.module {
        single<IDatabaseDriverFactory> { DatabaseDriverFactory() }

        single { environment }
        single { tokenRepository }
    }

}
