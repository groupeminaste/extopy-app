package com.extopy.di

import com.extopy.BuildConfig
import com.extopy.database.DatabaseDriverFactory
import com.extopy.database.IDatabaseDriverFactory
import com.extopy.models.application.ExtopyEnvironment
import org.koin.dsl.module

val environmentModule = module {
    single {
        if (BuildConfig.FLAVOR == "dev") ExtopyEnvironment.DEVELOPMENT
        else ExtopyEnvironment.PRODUCTION
    }
}

val databaseModule = module {
    single<IDatabaseDriverFactory> { DatabaseDriverFactory(get()) }
}

val androidModule = listOf(
    databaseModule,
    environmentModule,
)
