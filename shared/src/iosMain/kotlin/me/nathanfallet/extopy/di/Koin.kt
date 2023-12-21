package me.nathanfallet.extopy.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun KoinApplication.Companion.start(): KoinApplication = startKoin {
    modules(sharedModule)
}

// MARK: - Use cases (we should not call any other class from iOS directly, only use cases)
