package com.extopy.di

import com.extopy.database.DatabaseDriverFactory
import com.extopy.database.IDatabaseDriverFactory
import com.extopy.models.application.ExtopyEnvironment

object SwiftModule {

    lateinit var environment: ExtopyEnvironment

    fun module() = org.koin.dsl.module {
        single<IDatabaseDriverFactory> { DatabaseDriverFactory() }

        single { environment }
    }

}
