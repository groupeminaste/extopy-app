package me.nathanfallet.extopy.di

import me.nathanfallet.extopy.BuildConfig
import me.nathanfallet.extopy.database.DatabaseDriverFactory
import me.nathanfallet.extopy.models.application.ExtopyEnvironment
import me.nathanfallet.extopy.repositories.application.ITokenRepository
import me.nathanfallet.extopy.repositories.application.TokenRepository
import org.koin.dsl.module

val environmentModule = module {
    single {
        if (BuildConfig.FLAVOR == "dev") ExtopyEnvironment.DEVELOPMENT
        else ExtopyEnvironment.PRODUCTION
    }
}

val databaseModule = module {
    single { DatabaseDriverFactory(get()) }
}

val repositoryModule = module {
    single<ITokenRepository> { TokenRepository(get()) }
}

val androidModule = listOf(
    databaseModule,
    environmentModule,
    repositoryModule
)
