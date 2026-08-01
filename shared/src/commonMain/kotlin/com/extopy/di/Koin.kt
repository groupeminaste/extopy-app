package com.extopy.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

/** All app modules, given the platform-specific one. */
fun appModules(platformModule: Module): List<Module> =
    listOf(dataModule, domainModule, presentationModule, platformModule)

/** Starts Koin once at app launch. The platform module is created by the entry point. */
fun initKoin(
    platformModule: Module,
    appDeclaration: KoinAppDeclaration = {},
): KoinApplication =
    startKoin {
        appDeclaration()
        modules(appModules(platformModule))
    }
