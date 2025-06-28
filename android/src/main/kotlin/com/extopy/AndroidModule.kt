package com.extopy

import android.content.Context
import com.extopy.database.DatabaseDriverFactory
import com.extopy.database.IDatabaseDriverFactory
import com.extopy.models.application.ExtopyEnvironment
import dev.kaccelero.repositories.INativeSettingsRepository
import dev.kaccelero.repositories.NativeSettingsRepository
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

val repositoryModule = module {
    single<INativeSettingsRepository> {
        NativeSettingsRepository(get<Context>().getSharedPreferences("extopy", Context.MODE_PRIVATE))
    }
}

val androidModule = listOf(
    repositoryModule,
    databaseModule,
    environmentModule,
)
