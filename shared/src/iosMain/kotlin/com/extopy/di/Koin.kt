package com.extopy.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun KoinApplication.Companion.start(): KoinApplication = startKoin {
    modules(sharedModule)
}
